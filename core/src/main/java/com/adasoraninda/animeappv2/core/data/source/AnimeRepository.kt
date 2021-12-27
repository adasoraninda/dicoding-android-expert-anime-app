package com.adasoraninda.animeappv2.core.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.adasoraninda.animeappv2.core.data.source.local.LocalDataSource
import com.adasoraninda.animeappv2.core.data.source.remote.RemoteDataSource
import com.adasoraninda.animeappv2.core.data.source.remote.network.ApiResponse
import com.adasoraninda.animeappv2.core.data.source.remote.response.AnimeResponse
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.model.AnimeCharacter
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.core.domain.model.AnimeStaff
import com.adasoraninda.animeappv2.core.domain.repository.IAnimeRepository
import com.adasoraninda.animeappv2.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Suppress("UNCHECKED_CAST")
@Singleton
class AnimeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAnimeRepository {

    override fun getAllAnime(): Flow<PagingData<Anime>> {
        return object : NetworkBoundResource<PagingData<Anime>, List<AnimeResponse>>() {
            override fun loadFromDB(): Flow<PagingData<Anime>> {
                return Pager(
                    PagingConfig(
                        enablePlaceholders = false,
                        pageSize = 5,
                    )
                ) {
                    localDataSource.getAllAnime()
                }.flow.map { list -> list.map { DataMapper.mapEntityToDomain(it) } }
            }

            override fun shouldFetch(data: PagingData<Anime>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<AnimeResponse>>> {
                return remoteDataSource.getAllAnime()
            }

            override suspend fun saveCallResult(data: List<AnimeResponse>) {
                val animeList = data.map { response -> DataMapper.mapResponseToEntity(response) }
                localDataSource.insertListAnime(animeList)
            }
        }.asFlow()
    }

    override fun getFavoriteAnime(): Flow<PagingData<Anime>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 5,
            )
        ) {
            localDataSource.getFavoriteAnime()
        }.flow.map { paging ->
            paging.map { entity -> DataMapper.mapEntityToDomain(entity) }
        }
    }

    override fun getDetailAnime(id: Int?): Flow<Resource<AnimeDetail>> {
        return flow {
            emit(Resource.Loading())

            remoteDataSource.getDetailAnime(id).collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(
                        Resource.Success(
                            DataMapper.mapDetailResponseToDomain(
                                response.data
                            )
                        )
                    )
                    is ApiResponse.Empty -> emit(Resource.Error(null, null))
                    is ApiResponse.Error -> emit(Resource.Error(response.errorMessage, null))
                }
            }
        }
    }

    override fun searchAnime(anime: String?): Flow<PagingData<Anime>> {
        return remoteDataSource.searchAnime(anime).map { paging ->
            paging.map { response -> DataMapper.mapResponseToDomain(response) }
        }
    }

    override fun getDetailAnimeCharacter(id: Int?): Flow<PagingData<AnimeCharacter>> {
        return remoteDataSource.getDetailAnimeCharacter(id).map { paging ->
            paging.map { response -> DataMapper.mapCharacterResponseToDomain(response) }
        }
    }

    override fun getDetailAnimeStaff(id: Int?): Flow<PagingData<AnimeStaff>> {
        return remoteDataSource.getDetailAnimeStaff(id).map { paging ->
            paging.map { response -> DataMapper.mapStaffResponseToDomain(response) }
        }
    }

    override suspend fun setFavoriteAnime(anime: Anime) {
        val animeEntity = DataMapper.mapDomainToEntity(anime)
        localDataSource.setFavoriteAnime(animeEntity)
    }

    override fun getAnimeById(id: Int?): Flow<Anime?> {
        return localDataSource.getAnimeById(id).map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override suspend fun deleteAllFavoriteAnime() {
        localDataSource.deleteAllFavoriteAnime()
    }
}