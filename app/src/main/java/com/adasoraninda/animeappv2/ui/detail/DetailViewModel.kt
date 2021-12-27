package com.adasoraninda.animeappv2.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.adasoraninda.animeappv2.core.data.source.Resource
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.core.utils.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: AnimeUseCase
) : ViewModel() {

    private val _animeId = MutableLiveData<Int?>()

    private val _animeDetail = Transformations.switchMap(_animeId) { id ->
        useCase.getDetailAnime(id).asLiveData()
    }

    private val _anime = Transformations.switchMap(_animeId) { id ->
        useCase.getAnimeById(id).asLiveData()
    }

    private val _disableButton = MutableLiveData<Boolean>()
    val disableButton: LiveData<Boolean> get() = _disableButton

    private val _isAnimeFavored = Transformations.map(_anime) { anime ->
        anime?.isFavorite ?: false
    }

    private val _favoriteMessage = MutableLiveData<Boolean?>()
    val favoriteMessage: LiveData<Boolean?> get() = _favoriteMessage

    val animeDetail: LiveData<Resource<AnimeDetail>>
        get() = _animeDetail

    val isAnimeFavored: LiveData<Boolean>
        get() = _isAnimeFavored

    fun setAnimeId(id: Int?) {
        _animeId.value = id
    }

    @VisibleForTesting
    fun getAnimeId(): Int? {
        return _animeId.value
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _disableButton.value = true

            val animeDetail = _animeDetail.value?.data ?: return@launch
            val favoriteValue = _isAnimeFavored.value ?: false
            val animeDetailWithFavorite = animeDetail.copy(isFavorite = favoriteValue)
            val anime = DataMapper.mapDetailDomainToItemDomain(animeDetailWithFavorite)

            useCase.setFavoriteAnime(anime)
            setFavoriteMessage(favoriteValue)

            _disableButton.value = false
        }
    }

    @VisibleForTesting
    fun setFavoriteMessage(favorite: Boolean) {
        _favoriteMessage.value = !favorite
        _favoriteMessage.value = null
    }

    fun disableButton() {
        _disableButton.value = true
    }

}