package com.adasoraninda.animeappv2.ui.detail.staff

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.adasoraninda.animeappv2.core.domain.model.AnimeStaff
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val useCase: AnimeUseCase
) : ViewModel() {

    private val _animeId = MutableLiveData<Int?>()

    private val _animeDetailStaff = Transformations.switchMap(_animeId) { id ->
        useCase.getDetailAnimeStaff(id).asLiveData().cachedIn(viewModelScope)
    }

    val animeDetailStaff: LiveData<PagingData<AnimeStaff>>
        get() = _animeDetailStaff

    fun setAnimeId(id: Int?) {
        _animeId.value = id
    }

    @VisibleForTesting
    fun getAnimeId(): Int? {
        return _animeId.value
    }

}