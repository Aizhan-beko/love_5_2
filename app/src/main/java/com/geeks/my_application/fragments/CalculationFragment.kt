package com.geeks.my_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geeks.my_application.History.HistoryDao
import com.geeks.my_application.History.HistoryEntity
import com.geeks.my_application.LoveApi.LoveApiService
import com.geeks.my_application.LoveApi.LoveResult
import com.geeks.my_application.R
import com.geeks.my_application.databinding.FragmentCalculationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class CalculationFragment : Fragment() {

    @Inject lateinit var api: LoveApiService
    @Inject lateinit var historyDao: HistoryDao
    private lateinit var binding: FragmentCalculationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnCalculate.setOnClickListener {
            val firstName = binding.editFirst.text.toString()
            val secondName = binding.editSecond.text.toString()

            if (firstName.isBlank() || secondName.isBlank()) {
                Toast.makeText(context, "Please, enter both names", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            api.getPercentage(
                firstName = firstName,
                key = "b828eb47e0msh5b8e1cabea9eef6p152ea6jsn7e42ac9c829c",
                host = "love-calculator.p.rapidapi.com",
                secondName = secondName
            ).enqueue(object : Callback<LoveResult> {

                override fun onResponse(call: Call<LoveResult>, response: Response<LoveResult>) {
                    if (response.isSuccessful && response.body() != null) {
                        val loveResult = response.body()!!
                        saveToHistory(firstName, secondName, loveResult)
                        navigateToResultFragment(loveResult)
                    } else {
                        Toast.makeText(context, "Could not get a correct answer", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoveResult>, t: Throwable) {
                    Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun saveToHistory(firstName: String, secondName: String, loveResult: LoveResult) {
        lifecycleScope.launch {
            val historyEntity = HistoryEntity(
                firstName = firstName,
                secondName = secondName,
                result = loveResult.result,
                percentage = loveResult.percentage
            )
            historyDao.insertHistory(historyEntity)

        }
    }

    private fun navigateToResultFragment(loveResult: LoveResult) {
        val bundle = Bundle().apply {
            putString("percentage", loveResult.percentage)
            putString("result", loveResult.result)
        }
        findNavController().navigate(R.id.action_calculationFragment_to_resultFragment, bundle)
    }
}