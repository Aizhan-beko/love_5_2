package com.geeks.my_application.History

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.my_application.R
import com.geeks.my_application.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    @Inject lateinit var historyDao: HistoryDao
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter { historyEntity ->
            showDeleteDialog(historyEntity)
        }

        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            val historyList = historyDao.getAllHistorySorted()
            historyAdapter.submitList(historyList)
        }
        binding.backResultImg.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun showDeleteDialog(historyEntity: HistoryEntity) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_delete_confirmation_layout, null)

        dialogView.findViewById<TextView>(R.id.dialog_title).text = "Delete Item"
        dialogView.findViewById<TextView>(R.id.dialog_message).text = "Are you sure, you want to delete this item?"

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Yes") { dialog, _ ->
                lifecycleScope.launch {
                    historyDao.deleteHistory(historyEntity)
                    val updatedList = historyDao.getAllHistorySorted()
                    historyAdapter.submitList(updatedList)
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        builder.show()
    }
    }
