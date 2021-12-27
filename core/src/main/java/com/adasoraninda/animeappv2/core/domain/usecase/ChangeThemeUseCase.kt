package com.adasoraninda.animeappv2.core.domain.usecase

interface ChangeThemeUseCase {

    fun saveTheme(isDarkTheme: Boolean)
    fun isDarkTheme(): Boolean

}