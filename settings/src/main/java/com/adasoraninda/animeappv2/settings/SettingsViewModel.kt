package com.adasoraninda.animeappv2.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adasoraninda.animeappv2.core.domain.usecase.ChangeThemeUseCase

class SettingsViewModel(
    private val useCase: ChangeThemeUseCase
) : ViewModel() {

    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    fun saveTheme(isDarkTheme: Boolean) {
        useCase.saveTheme(isDarkTheme)
        _isDarkTheme.value = isDarkTheme
    }

    fun getTheme(): Boolean {
        return useCase.isDarkTheme()
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory(userCase: ChangeThemeUseCase) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(userCase) as T
            }
        }
    }

}