package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.ui.detail.information.InformationViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class InformationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun setDetailAnimeTest() {
        // arrange
        val expectedValue = mockAnimeDetail
        val viewModel = InformationViewModel()

        // act
        viewModel.setDetailAnime(expectedValue)
        val result = viewModel.detailAnime.testObserver().observedValue

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedValue, result)
    }

    companion object {
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