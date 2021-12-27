package com.adasoraninda.animeappv2.core.domain.repository

import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.data.source.Resource
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.model.AnimeCharacter
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.core.domain.model.AnimeStaff
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {

    fun getAllAnime(): Flow<PagingData<Anime>>

    fun getFavoriteAnime(): Flow<PagingData<Anime>>

    fun searchAnime(anime: String?): Flow<PagingData<Anime>>

    fun getDetailAnimeCharacter(id: Int?): Flow<PagingData<AnimeCharacter>>

    fun getDetailAnimeStaff(id: Int?): Flow<PagingData<AnimeStaff>>

    fun getDetailAnime(id: Int?): Flow<Resource<AnimeDetail>>

    suspend fun setFavoriteAnime(anime: Anime)

    fun getAnimeById(id: Int?): Flow<Anime?>

    suspend fun deleteAllFavoriteAnime()
}