package com.adasoraninda.animeappv2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListAnimeResponse(
    @field:SerializedName("top")
    val anime: List<AnimeResponse>? = null
)




