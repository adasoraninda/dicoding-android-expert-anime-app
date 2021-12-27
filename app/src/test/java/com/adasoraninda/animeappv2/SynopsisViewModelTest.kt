package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adasoraninda.animeappv2.ui.detail.synopsis.SynopsisViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SynopsisViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun setSynopsisTest() {
        // arrange
        val expectedValue = mockSynopsis
        val viewModel = SynopsisViewModel()

        // act
        viewModel.setSynopsis(expectedValue)
        val result = viewModel.synopsis.testObserver().observedValue

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedValue, result)
        Assert.assertEquals(expectedValue.length, result?.length)
    }

    companion object {
        private const val mockSynopsis = "Synopsis Anime"
    }

}