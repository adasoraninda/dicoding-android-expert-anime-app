package com.adasoraninda.animeappv2.core.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.data.source.remote.network.ApiResponse
import com.adasoraninda.animeappv2.core.data.source.remote.network.ApiService
import com.adasoraninda.animeappv2.core.data.source.remote.network.paging.CharacterAnimeRemotePagingSource
import com.adasoraninda.animeappv2.core.data.source.remote.network.paging.SearchAnimeRemotePagingSource
import com.adasoraninda.animeappv2.core.data.source.remote.network.paging.StaffAnimeRemotePagingSource
import com.adasoraninda.animeappv2.core.data.source.remote.response.AnimeResponse
import com.adasoraninda.animeappv2.core.data.source.remote.response.CharactersResponse
import com.adasoraninda.animeappv2.core.data.source.remote.response.DetailAnimeResponse
import com.adasoraninda.animeappv2.core.data.source.remote.response.StaffResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllAnime(): Flow<ApiResponse<List<AnimeResponse>>> {
        return flow {
            try {
                val response = apiService.getListAnime()
                val dataArray = response.anime
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailAnime(id: Int?): Flow<ApiResponse<DetailAnimeResponse>> {
        return flow {
            try {
                val response = apiService.getDetailAnime(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchAnime(anime: String?): Flow<PagingData<AnimeResponse>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 5,
            )
        ) {
            SearchAnimeRemotePagingSource(anime, apiService)
        }.flow
    }

    fun getDetailAnimeCharacter(id: Int?): Flow<PagingData<CharactersResponse>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 5
            )
        ) {
            CharacterAnimeRemotePagingSource(id, apiService)
        }.flow
    }

    fun getDetailAnimeStaff(id: Int?): Flow<PagingData<StaffResponse>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 5
            )
        ) {
            StaffAnimeRemotePagingSource(id, apiService)
        }.flow
    }


}