package com.adasoraninda.animeappv2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adasoraninda.animeappv2.ui.splash.SplashViewModel
import com.adasoraninda.animeappv2.utils.testObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class SplashViewModelTest {

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
    fun navigateToHomeTest() {
        // arrange
        val expectedValue = true
        val viewModel = SplashViewModel()

        // act
        viewModel.navigateToHome(0)
        val result = viewModel.navigate.testObserver().observedValue

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedValue, result)
    }


    @Test
    fun doneNavigateTest() {
        // arrange
        val expectedValue = false
        val viewModel = SplashViewModel()

        // act
        viewModel.doneNavigate()
        val result = viewModel.navigate.testObserver().observedValue

        // assert
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedValue, result)
    }

}