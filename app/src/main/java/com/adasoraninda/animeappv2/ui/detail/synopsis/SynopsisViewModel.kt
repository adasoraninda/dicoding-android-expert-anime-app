package com.adasoraninda.animeappv2.ui.detail.synopsis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SynopsisViewModel @Inject constructor() : ViewModel() {

    private val _synopsis = MutableLiveData<String?>()
    val synopsis: LiveData<String?> get() = _synopsis

    fun setSynopsis(synopsis: String?) {
        _synopsis.value = synopsis
    }

}