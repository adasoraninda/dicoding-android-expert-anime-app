package com.adasoraninda.animeappv2.core.di

import android.content.Context
import androidx.room.Room
import com.adasoraninda.animeappv2.core.data.source.local.room.AnimeDao
import com.adasoraninda.animeappv2.core.data.source.local.room.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideOpenHelperFactory(): SupportFactory {
        val passPhrase = SQLiteDatabase.getBytes("anime".toCharArray())
        return SupportFactory(passPhrase)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        factory: SupportFactory
    ): AnimeDatabase {
        return Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            "Anime.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideAnimeDao(database: AnimeDatabase): AnimeDao = database.animeDao

}