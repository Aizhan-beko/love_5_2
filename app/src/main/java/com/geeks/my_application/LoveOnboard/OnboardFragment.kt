package com.geeks.my_application.LoveOnboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.geeks.my_application.LoveOnboard.onboardViewPagerAdapter.OnBoardViewPagerAdapter
import com.geeks.my_application.R
import com.geeks.my_application.sharedpreference.SharedPreferencesHelper
import com.geeks.my_application.databinding.FragmentOnboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class OnboardFragment : Fragment() {

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var binding: FragmentOnboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
    }
    private fun initialize() {
        val viewPager2 = binding.viewPager2
        binding.viewPager2.adapter = OnBoardViewPagerAdapter(this)
    }

    private fun setupListener() = with(binding.viewPager2) {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 3) {
                    binding.btnStart.visibility = View.VISIBLE
                } else {
                    binding.btnStart.visibility = View.INVISIBLE

                }
            }
        })
        binding.btnStart.setOnClickListener {
            if (currentItem < 4) {
                setCurrentItem(currentItem + 3)
            }
        }
        binding.btnStart.setOnClickListener{
            sharedPreferencesHelper.setOnboardingShown()
            findNavController().navigate(R.id.action_onboardFragment_to_calculationFragment)
        }
    }
}
