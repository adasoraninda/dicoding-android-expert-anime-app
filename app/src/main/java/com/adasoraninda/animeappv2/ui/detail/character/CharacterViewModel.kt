package com.adasoraninda.animeappv2.ui.detail.character

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.adasoraninda.animeappv2.core.domain.model.AnimeCharacter
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val useCase: AnimeUseCase
) : ViewModel() {

    private val _animeId = MutableLiveData<Int?>()

    private val _animeDetailCharacters = Transformations.switchMap(_animeId) { id ->
        useCase.getDetailAnimeCharacter(id).asLiveData().cachedIn(viewModelScope)
    }

    val animeDetailCharacters: LiveData<PagingData<AnimeCharacter>>
        get() = _animeDetailCharacters

    fun setAnimeId(id: Int?) {
        _animeId.value = id
    }

    @VisibleForTesting
    fun getAnimeId(): Int? {
        return _animeId.value
    }

}