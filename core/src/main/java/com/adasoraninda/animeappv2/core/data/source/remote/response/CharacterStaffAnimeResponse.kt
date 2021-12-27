package com.adasoraninda.animeappv2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CharacterStaffAnimeResponse(
    @field:SerializedName("characters")
    val characters: List<CharactersResponse>? = null,
    @field:SerializedName("staff")
    val staff: List<StaffResponse>? = null,
)

data class VoiceActorsResponse(

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("language")
    val language: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class StaffResponse(

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("positions")
    val positions: List<String>? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class CharactersResponse(

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("voice_actors")
    val voiceActors: List<VoiceActorsResponse>? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)
