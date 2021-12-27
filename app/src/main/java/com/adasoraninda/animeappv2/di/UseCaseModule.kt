package com.adasoraninda.animeappv2.di

import com.adasoraninda.animeappv2.core.domain.usecase.AnimeInteractor
import com.adasoraninda.animeappv2.core.domain.usecase.AnimeUseCase
import com.adasoraninda.animeappv2.core.domain.usecase.ChangeThemeInteractor
import com.adasoraninda.animeappv2.core.domain.usecase.ChangeThemeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindAnimeUseCase(useCase: AnimeInteractor): AnimeUseCase

    @Binds
    fun bindThemeUseCase(useCase: ChangeThemeInteractor): ChangeThemeUseCase

}