package com.adasoraninda.animeappv2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchAnimeResponse(

    @field:SerializedName("last_page")
    val lastPage: Int? = null,

    @field:SerializedName("results")
    val results: List<AnimeResponse>? = null

)

