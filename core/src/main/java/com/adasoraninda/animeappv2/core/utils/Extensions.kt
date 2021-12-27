package com.adasoraninda.animeappv2.core.utils

import androidx.appcompat.app.AppCompatDelegate

fun changeTheme(isDarkMode: Boolean) {
    val mode = if (isDarkMode) {
        AppCompatDelegate.MODE_NIGHT_YES
    } else {
        AppCompatDelegate.MODE_NIGHT_NO
    }
    AppCompatDelegate.setDefaultNightMode(mode)
}