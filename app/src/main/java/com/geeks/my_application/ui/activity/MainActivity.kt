package com.geeks.my_application.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.geeks.my_application.data.local.HistoryDao
import com.geeks.my_application.data.network.LoveApiService
import com.geeks.my_application.R
import com.geeks.my_application.application.SharedPreferencesHelper
import com.geeks.my_application.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    @Inject
    lateinit var loveApiService: LoveApiService
    @Inject
    lateinit var historyDao: HistoryDao

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (sharedPreferencesHelper.isOnboardingShown()) {
            navController.navigate(R.id.action_global_calculationFragment)
        } else {
            navController.navigate(R.id.action_global_onboardFragment)
        }
    }
}