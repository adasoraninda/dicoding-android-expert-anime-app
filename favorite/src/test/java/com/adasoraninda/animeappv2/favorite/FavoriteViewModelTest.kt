package com.adasoraninda.animeappv2.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

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
    fun removeFavoriteTest() = runBlockingTest {
        // arrange
        val mockData = mockAnime
        val mockUseCase = mock(AnimeUseCase::class.java)

        `when`(mockUseCase.getFavoriteAnime())
            .thenReturn(flowOf(PagingData.empty()))

        val viewModel = FavoriteViewModel(mockUseCase)

        // act
        viewModel.removeFavoriteAnime(mockData)

        // assert
        verify(mockUseCase, atLeastOnce()).setFavoriteAnime(mockData)
    }

    @Test
    fun removeAllFavoriteTest() = runBlockingTest {
        // arrange
        val mockUseCase = mock(AnimeUseCase::class.java)

        `when`(mockUseCase.getFavoriteAnime())
            .thenReturn(flowOf(PagingData.empty()))

        val viewModel = FavoriteViewModel(mockUseCase)

        // act
        viewModel.removeAllFavoriteAnime()

        // assert
        verify(mockUseCase, atLeastOnce()).deleteAllFavoriteAnime()
    }

    companion object {
        private val mockAnime =
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

    }

}