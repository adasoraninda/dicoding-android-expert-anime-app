package com.adasoraninda.animeappv2.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val useCase: AnimeUseCase
) : ViewModel() {

    val favoriteAnime = useCase.getFavoriteAnime().asLiveData().cachedIn(viewModelScope)

    fun removeFavoriteAnime(anime: Anime) {
        viewModelScope.launch {
            useCase.setFavoriteAnime(anime)
        }
    }

    fun removeAllFavoriteAnime() {
        viewModelScope.launch {
            useCase.deleteAllFavoriteAnime()
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory(userCase: AnimeUseCase) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FavoriteViewModel(userCase) as T
            }
        }
    }

}