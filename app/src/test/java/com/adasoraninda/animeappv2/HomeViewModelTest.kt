package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.ui.home.HomeViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

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
    fun shouldGetAnimeValue() {
        // arrange
        val mockUseCase = mock(AnimeUseCase::class.java)
        val mockPaging = PagingData.from(mockListAnime)
        val mockFlow = flowOf(mockPaging)

        `when`(mockUseCase.getAllAnime())
            .thenReturn(mockFlow)

        val viewModel = HomeViewModel(mockUseCase)

        // act
        val result = viewModel.anime.testObserver().observedValue

        // assert
        verify(mockUseCase, atLeastOnce()).getAllAnime()
        verifyNoMoreInteractions(mockUseCase)
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