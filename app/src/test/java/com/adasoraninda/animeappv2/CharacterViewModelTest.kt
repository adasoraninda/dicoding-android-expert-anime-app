package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.domain.model.AnimeCharacter
import com.adasoraninda.animeappv2.core.domain.model.AnimeVoiceActor
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.ui.detail.character.CharacterViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

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
        val mockUseCase = Mockito.mock(AnimeUseCase::class.java)
        val viewModel = CharacterViewModel(mockUseCase)

        // act
        viewModel.setAnimeId(animeId)
        val result = viewModel.getAnimeId()

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(animeId, result)
    }

    @Test
    fun shouldGetAnimeCharacterValueFromSetAnimeId() {
        // arrange
        val animeId = animeId
        val mockUseCase = Mockito.mock(AnimeUseCase::class.java)
        val mockPaging = PagingData.from(mockListAnimeCharacter)
        val mockFlow = flowOf(mockPaging)

        Mockito.`when`(mockUseCase.getDetailAnimeCharacter(animeId))
            .thenReturn(mockFlow)

        val viewModel = CharacterViewModel(mockUseCase)

        // act
        viewModel.setAnimeId(animeId)
        val result = viewModel.animeDetailCharacters.testObserver().observedValue

        // assert
        Mockito.verify(mockUseCase).getDetailAnimeCharacter(animeId)
        Mockito.verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
    }

    companion object {
        private const val animeId = 1

        private val mockListAnimeCharacter = listOf(
            AnimeCharacter(
                name = "ada",
                imageUrl = "",
                malId = 123,
                url = "",
                role = "",
                voiceActor = AnimeVoiceActor(
                    name = "ada2",
                    imageUrl = "",
                    malId = 321,
                    language = "",
                    url = "",
                ),
            )
        )
    }

}