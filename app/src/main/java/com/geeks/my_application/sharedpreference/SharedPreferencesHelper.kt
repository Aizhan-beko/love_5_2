package com.geeks.my_application.sharedpreference

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPreferencesHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val PREF_ONBOARDING_SHOWN = "onboarding_shown"
    }

    fun isOnboardingShown(): Boolean {
        return sharedPreferences.getBoolean(PREF_ONBOARDING_SHOWN, false)
    }

    fun setOnboardingShown() {
        sharedPreferences.edit().putBoolean(PREF_ONBOARDING_SHOWN, true).apply()
    }
}