package com.adasoraninda.animeappv2.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: AnimeUseCase
) : ViewModel() {

    private val _anime = MutableLiveData<PagingData<Anime>>()
    val anime: LiveData<PagingData<Anime>>
        get() = _anime

    private val _query = MutableLiveData<String?>()

    fun setSearchQuery(query: String?) {
        viewModelScope.launch {
            flowOf(query)
                .debounce(300L)
                .filter { q ->
                    if (q?.trim().isNullOrEmpty()) {
                        _anime.value = PagingData.empty()
                        return@filter false
                    }

                    if (q?.trim() == _query.value) {
                        return@filter false
                    }

                    return@filter true
                }
                .distinctUntilChanged()
                .onEach { _query.value = it?.trim() }
                .collectLatest {
                    searchAnime(it)
                }
        }
    }

    fun searchAnime(query: String?) {
        viewModelScope.launch {
            useCase.searchAnime(query).cachedIn(this).collect {
                _anime.value = it
            }
        }
    }

}