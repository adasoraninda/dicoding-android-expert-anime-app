package com.adasoraninda.animeappv2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCase: AnimeUseCase
) : ViewModel() {

    val anime = useCase.getAllAnime().asLiveData().cachedIn(viewModelScope)

}