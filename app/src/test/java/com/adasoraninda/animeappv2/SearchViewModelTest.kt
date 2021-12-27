package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.ui.search.SearchViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setSearchQueryTest() {
        // arrange
        val query = "Fate"
        val mockUseCase = Mockito.mock(AnimeUseCase::class.java)
        val mockPaging = PagingData.from(mockListAnime)
        val mockFlow = flowOf(mockPaging)

        Mockito.`when`(mockUseCase.searchAnime(query))
            .thenReturn(mockFlow)

        val viewModel = SearchViewModel(mockUseCase)

        // act
        viewModel.setSearchQuery(query)
        val result = viewModel.anime.testObserver().observedValue

        // assert
        Mockito.verify(mockUseCase, Mockito.atLeastOnce()).searchAnime(query)
        Mockito.verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
    }

    @Test
    fun searchAnimeTest() {
        // arrange
        val query = "Fate"
        val mockUseCase = Mockito.mock(AnimeUseCase::class.java)
        val mockPaging = PagingData.from(mockListAnime)
        val mockFlow = flowOf(mockPaging)

        Mockito.`when`(mockUseCase.searchAnime(query))
            .thenReturn(mockFlow)

        val viewModel = SearchViewModel(mockUseCase)

        // act
        viewModel.searchAnime(query)
        val result = viewModel.anime.testObserver().observedValue

        // assert
        Mockito.verify(mockUseCase, Mockito.atLeastOnce()).searchAnime(query)
        Mockito.verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
    }

    companion object {
        private val mockListAnime = listOf(
            Anime(
                malId = 11741,
                type = "TV",
                episodes = 12,
                score = 8.58,
                members = 951782,
                isFavorite = false,
                title = "Fate/Zero 2nd Season",
                imageUrl = "https://cdn.myanimelist.net/images/anime/8/41125.jpg",
            )
        )
    }

}