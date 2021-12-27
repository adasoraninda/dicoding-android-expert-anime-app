package com.adasoraninda.animeappv2.di

import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.core.domain.usecase.ChangeThemeUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface UseCaseEntryPoint {
    fun animeUseCase(): AnimeUseCase
    fun changeThemeUseCase(): ChangeThemeUseCase
}