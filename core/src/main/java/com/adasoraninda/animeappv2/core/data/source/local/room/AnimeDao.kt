package com.adasoraninda.animeappv2.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adasoraninda.animeappv2.core.data.source.local.enitty.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime")
    fun getAllAnime(): PagingSource<Int, AnimeEntity>

    @Query("SELECT * FROM anime WHERE is_favorite = 1")
    fun getFavoriteAnime(): PagingSource<Int, AnimeEntity>

    @Query("SELECT * FROM anime WHERE mal_id = :id AND is_favorite = 1 LIMIT 1")
    fun getAnimeById(id: Int?): Flow<AnimeEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListAnime(animeList: List<AnimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeEntity)

    @Query("UPDATE anime SET is_favorite = 0 WHERE is_favorite = 1")
    suspend fun deleteAllFavoriteAnime()

}