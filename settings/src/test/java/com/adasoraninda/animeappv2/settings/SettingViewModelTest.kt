package com.adasoraninda.animeappv2.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adasoraninda.animeappv2.core.domain.usecase.ChangeThemeUseCase
import com.adasoraninda.animeappv2.settings.utils.testObserver
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class SettingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun setThemeTest() {
        // arrange
        val mockUseCase = mock(ChangeThemeUseCase::class.java)
        val viewModel = SettingsViewModel(mockUseCase)
        val expectedValue = true

        // act
        viewModel.saveTheme(expectedValue)
        val result = viewModel.isDarkTheme.testObserver().observedValue

        // assert
        verify(mockUseCase, only()).saveTheme(expectedValue)
        verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedValue, result)
    }

    @Test
    fun getThemeTest() {
        // arrange
        val mockUseCase = mock(ChangeThemeUseCase::class.java)
        val viewModel = SettingsViewModel(mockUseCase)
        val expectedValue = true

        `when`(mockUseCase.isDarkTheme())
            .thenReturn(expectedValue)

        // act
        val result = viewModel.getTheme()

        // assert
        verify(mockUseCase, only()).isDarkTheme()
        verifyNoMoreInteractions(mockUseCase)
        Assert.assertNotNull(result)
        Assert.assertEquals(expectedValue, result)
    }

}