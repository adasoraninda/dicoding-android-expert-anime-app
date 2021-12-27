package com.adasoraninda.animeappv2.core.data.source.local

import androidx.paging.PagingSource

import com.adasoraninda.animeappv2.core.data.source.local.enitty.AnimeEntity
import com.adasoraninda.animeappv2.core.data.source.local.room.AnimeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val animeDao: AnimeDao
) {
    fun getAllAnime(): PagingSource<Int, AnimeEntity> = animeDao.getAllAnime()

    fun getFavoriteAnime(): PagingSource<Int, AnimeEntity> = animeDao.getFavoriteAnime()

    fun getAnimeById(id: Int?): Flow<AnimeEntity?> = animeDao.getAnimeById(id)

    suspend fun insertListAnime(animeList: List<AnimeEntity>) = animeDao.insertListAnime(animeList)

    suspend fun setFavoriteAnime(anime: AnimeEntity) {
        animeDao.insertAnime(anime)
    }

    suspend fun deleteAllFavoriteAnime() {
        animeDao.deleteAllFavoriteAnime()
    }

}