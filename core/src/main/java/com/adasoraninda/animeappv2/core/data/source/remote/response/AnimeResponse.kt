package com.adasoraninda.animeappv2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AnimeResponse(

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("score")
    val score: Double? = null,

    @field:SerializedName("members")
    val members: Int? = null,

    @field:SerializedName("episodes")
    val episodes: Int? = null,

)
