package com.geeks.my_application.ui.fragments.calculate_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.geeks.my_application.ui.fragments.result_fragment.LoveResult
import com.geeks.my_application.R
import com.geeks.my_application.databinding.FragmentCalculationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CalculationFragment : Fragment() {

    private var _binding: FragmentCalculationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(this)[CalculationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupObservers()
    }

    private fun setupObservers(){
        viewModel.loveResultData.observe(viewLifecycleOwner){result->
            navigateToResultFragment(result)
        }
        viewModel.errorData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupClickListener() {
        binding.btnCalculate.setOnClickListener {
            val firstName = binding.editFirst.text.toString()
            val secondName = binding.editSecond.text.toString()

            if (firstName.isBlank() || secondName.isBlank()) {
                Toast.makeText(context, "Please, enter both names", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.getPercentage(firstName, secondName)
        }
    }

    private fun navigateToResultFragment(loveResult: LoveResult) {
        val bundle = Bundle().apply {
            putString("percentage", loveResult.percentage)
            putString("result", loveResult.result)
        }
        findNavController().navigate(R.id.action_calculationFragment_to_resultFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}