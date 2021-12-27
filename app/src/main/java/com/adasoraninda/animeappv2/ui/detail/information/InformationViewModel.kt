package com.adasoraninda.animeappv2.ui.detail.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor() : ViewModel() {

    private val _detailAnime = MutableLiveData<AnimeDetail?>()
    val detailAnime: LiveData<AnimeDetail?> get() = _detailAnime

    fun setDetailAnime(animeDetail: AnimeDetail?) {
        _detailAnime.value = animeDetail
    }

}