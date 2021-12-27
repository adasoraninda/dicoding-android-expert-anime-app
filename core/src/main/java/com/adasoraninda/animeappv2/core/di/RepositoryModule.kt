package com.adasoraninda.animeappv2.core.di

import com.adasoraninda.animeappv2.core.data.source.AnimeRepository
import com.adasoraninda.animeappv2.core.domain.repository.IAnimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideRepository(animeRepository: AnimeRepository): IAnimeRepository

}