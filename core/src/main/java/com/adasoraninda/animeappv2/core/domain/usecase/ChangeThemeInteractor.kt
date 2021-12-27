package com.adasoraninda.animeappv2.core.domain.usecase

import com.adasoraninda.animeappv2.core.data.source.local.SettingsPreferences
import javax.inject.Inject

class ChangeThemeInteractor @Inject constructor(
    private val pref: SettingsPreferences
) : ChangeThemeUseCase {

    override fun saveTheme(isDarkTheme: Boolean) {
        pref.setTheme(isDarkTheme)
    }

    override fun isDarkTheme(): Boolean {
        return pref.getTheme()
    }
}