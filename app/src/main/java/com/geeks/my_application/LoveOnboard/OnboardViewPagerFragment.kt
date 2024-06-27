package com.geeks.my_application.LoveOnboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geeks.my_application.R
import com.geeks.my_application.databinding.FragmentOnboardViewPagerBinding


class OnboardViewPagerFragment : Fragment() {

    private lateinit var  binding: FragmentOnboardViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                tvLove.text = "Welcome to Love Calculator APP"
                imgLove.setImageResource(R.drawable.love_1)
            }
            1 -> {
                tvLove.text = "Calculate love compatibility "
                imgLove.setImageResource(R.drawable.love_2)
            }
            2 -> {
                tvLove.text = "Try your chance and get married"
                imgLove.setImageResource(R.drawable.get_married)
            }
            3 -> {
                tvLove.text = "or terminate relationship"
                imgLove.setImageResource(R.drawable.love_4)

            }
        }
    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}
