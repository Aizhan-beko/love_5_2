package com.geeks.my_application.ui.fragments.onboarding_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardViewPagerAdapter (fragment: Fragment
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int) = OnboardViewPagerFragment().apply{
        arguments = Bundle().apply{
            putInt(OnboardViewPagerFragment.ARG_ONBOARD_POSITION, position)

        }
    }
}