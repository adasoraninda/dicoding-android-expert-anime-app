package com.adasoraninda.animeappv2.ui.splash

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate

    private val delay = 2_000L

    init {
        navigateToHome(delay)
    }

    @VisibleForTesting
    fun navigateToHome(delay: Long) {
        viewModelScope.launch {
            delay(delay)
            _navigate.value = true
        }
    }

    fun doneNavigate() {
        _navigate.value = false
    }

}