package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.domain.model.AnimeStaff
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.ui.detail.staff.StaffViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class StaffViewModelTest {

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
    fun setAnimeIdTest() {
        // arrange
        val animeId = animeId
        val mockUseCase = mock(AnimeUseCase::class.java)
        val viewModel = StaffViewModel(mockUseCase)

        // act
        viewModel.setAnimeId(animeId)
        val result = viewModel.getAnimeId()

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(animeId, result)
    }

    @Test
    fun shouldGetAnimeStaffValueFromSetAnimeId() {
        // arrange
        val animeId = animeId
        val mockUseCase = mock(AnimeUseCase::class.java)
        val mockPaging = PagingData.from(mockListAnimeStaff)
        val mockFlow = flowOf(mockPaging)

        `when`(mockUseCase.getDetailAnimeStaff(animeId))
            .thenReturn(mockFlow)

        val viewModel = StaffViewModel(mockUseCase)

        // act
        viewModel.setAnimeId(animeId)
        val result = viewModel.animeDetailStaff.testObserver().observedValue

        // assert
        verify(mockUseCase).getDetailAnimeStaff(animeId)
        verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
    }

    companion object {
        private const val animeId = 1

        private val mockListAnimeStaff = listOf(
            AnimeStaff(
                name = "ada",
                imageUrl = "",
                positions = "",
                malId = 123,
                url = "",
            )
        )
    }

}