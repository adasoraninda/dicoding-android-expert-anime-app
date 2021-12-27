package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.adasoraninda.animeappv2.core.data.source.Resource
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.ui.detail.DetailViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockMessageObserver: Observer<Boolean?>

    @Captor
    private lateinit var captorMessage: ArgumentCaptor<Boolean>

    @Mock
    private lateinit var mockDisableObserver: Observer<Boolean?>

    @Captor
    private lateinit var captorDisable: ArgumentCaptor<Boolean>

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
        val animeId = mockAnimeDetail.malId
        val mockUseCase = mock(AnimeUseCase::class.java)
        val viewModel = DetailViewModel(mockUseCase)

        // act
        viewModel.setAnimeId(animeId)
        val result = viewModel.getAnimeId()

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(animeId, result)
    }

    @Test
    fun shouldGetDetailAnimeValueFromSetAnimeId() {
        // arrange
        val animeId = mockAnimeDetail.malId
        val mockUseCase = mock(AnimeUseCase::class.java)
        val mockFlow = flowOf(Resource.Success(mockAnimeDetail))

        `when`(mockUseCase.getDetailAnime(animeId))
            .thenReturn(mockFlow)

        val viewModel = DetailViewModel(mockUseCase)

        // act
        viewModel.setAnimeId(animeId)
        val result = viewModel.animeDetail.testObserver().observedValue

        // assert
        verify(mockUseCase, only()).getDetailAnime(animeId)
        verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
        Assert.assertEquals(animeId, result?.data?.malId)
    }

    @Test
    fun shouldGetIsAnimeFavoriteValueFromSetAnimeId() {
        // arrange
        val animeId = mockAnime.malId
        val mockUseCase = mock(AnimeUseCase::class.java)
        val mockFlow = flowOf(mockAnime)
        val expectedFavored = true

        `when`(mockUseCase.getAnimeById(animeId))
            .thenReturn(mockFlow)

        val viewModel = DetailViewModel(mockUseCase)

        // act
        viewModel.setAnimeId(animeId)
        val result = viewModel.isAnimeFavored.testObserver().observedValue

        // assert
        verify(mockUseCase, atLeastOnce()).getAnimeById(animeId)
        verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedFavored, result)
    }

    @Test
    fun toggleFavoriteTest() {
        // arrange
        val mockUseCase = mock(AnimeUseCase::class.java)
        val viewModel = DetailViewModel(mockUseCase)

        viewModel.disableButton.observeForever(mockDisableObserver)

        // act
        viewModel.toggleFavorite()

        // assert
        verify(mockDisableObserver, times(1))
            .onChanged(captorDisable.capture())

        val result = captorDisable.allValues
        Assert.assertNotNull(result)
        Assert.assertEquals(true, result[0])
    }

    @Test
    fun disableButtonTest() {
        // arrange
        val mockUseCase = mock(AnimeUseCase::class.java)
        val viewModel = DetailViewModel(mockUseCase)
        val expectedValue = true

        // act
        viewModel.disableButton()
        val result = viewModel.disableButton.testObserver().observedValue

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedValue, result)
    }

    @Test
    fun setFavoriteMessageFalseNullTest() {
        // arrange
        val mockUseCase = mock(AnimeUseCase::class.java)
        val viewModel = DetailViewModel(mockUseCase)
        val expectedValue = false

        viewModel.favoriteMessage.observeForever(mockMessageObserver)

        // act
        viewModel.setFavoriteMessage(true)

        // assert
        verify(mockMessageObserver, times(2))
            .onChanged(captorMessage.capture())

        val result = captorMessage.allValues

        Assert.assertNotNull(result[0])
        Assert.assertEquals(expectedValue, result[0])
        Assert.assertNull(result[1])
    }

    @Test
    fun setFavoriteMessageTrueNullTest() {
        // arrange
        val mockUseCase = mock(AnimeUseCase::class.java)
        val viewModel = DetailViewModel(mockUseCase)
        val expectedValue = true

        viewModel.favoriteMessage.observeForever(mockMessageObserver)

        // act
        viewModel.setFavoriteMessage(false)

        // assert
        verify(mockMessageObserver, times(2))
            .onChanged(captorMessage.capture())

        val result = captorMessage.allValues

        Assert.assertNotNull(result[0])
        Assert.assertEquals(expectedValue, result[0])
        Assert.assertNull(result[1])
    }

    companion object {
        private val mockAnime = Anime(
            malId = 11741,
            type = "TV",
            episodes = 12,
            score = 8.58,
            members = 951782,
            isFavorite = true,
            title = "Fate/Zero 2nd Season",
            imageUrl = "https://cdn.myanimelist.net/images/anime/8/41125.jpg",
        )

        private val mockAnimeDetail = AnimeDetail(
            malId = 11741,
            titleJapanese = "Fate/Zero 2nd Season",
            rating = "R - 17+ (violence & profanity)",
            synopsis = "As the Fourth Holy Grail War rages on with no clear victor in sight, the remaining Servants and their Masters are called upon by Church supervisor Risei Kotomine, in order to band together and confron...",
            type = "TV",
            episodes = 12,
            score = 8.58,
            members = 951782,
            favorites = 18528,
            isFavorite = false,
            broadcast = "Sundays at 00:00 (JST)",
            scoredBy = 601258,
            premiered = "Spring 2012",
            titleEnglish = "Fate/Zero Season 2",
            titleSynonyms = "Fate/Zero Second Season",
            source = "Light novel",
            duration = "24 min per ep",
            title = "Fate/Zero 2nd Season",
            genres = "action",
            popularity = 115,
            rank = 79,
            airing = false,
            aired = "2012-04-08",
            studios = "anime",
            imageUrl = "https://cdn.myanimelist.net/images/anime/8/41125.jpg",
            licensors = "Aniplex of America",
            url = "https://myanimelist.net/anime/11741/Fate_Zero_2nd_Season",
            trailerUrl = "https://www.youtube.com/embed/FkZ1euZ-S-Y?enablejsapi=1&wmode=opaque&autoplay=1",
            producers = "Aniplex",
            status = "Finished Airing",
        )
    }

}