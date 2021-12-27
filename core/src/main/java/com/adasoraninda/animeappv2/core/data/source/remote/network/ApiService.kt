package com.adasoraninda.animeappv2.core.data.source.remote.network

import com.adasoraninda.animeappv2.core.data.source.remote.response.CharacterStaffAnimeResponse
import com.adasoraninda.animeappv2.core.data.source.remote.response.DetailAnimeResponse
import com.adasoraninda.animeappv2.core.data.source.remote.response.ListAnimeResponse
import com.adasoraninda.animeappv2.core.data.source.remote.response.SearchAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("top/anime")
    suspend fun getListAnime(): ListAnimeResponse

    @GET("search/anime")
    suspend fun searchAnime(
        @Query("q") anime: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ): SearchAnimeResponse

    @GET("anime/{id}")
    suspend fun getDetailAnime(
        @Path("id") id: Int?
    ): DetailAnimeResponse

    @GET("anime/{id}/characters_staff")
    suspend fun getDetailAnimeCharacterStaff(
        @Path("id") id: Int?
    ): CharacterStaffAnimeResponse

}