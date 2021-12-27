package com.adasoraninda.animeappv2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailAnimeResponse(

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("title_japanese")
    val titleJapanese: String? = null,

    @field:SerializedName("favorites")
    val favorites: Int? = null,

    @field:SerializedName("broadcast")
    val broadcast: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("scored_by")
    val scoredBy: Int? = null,

    @field:SerializedName("premiered")
    val premiered: String? = null,

    @field:SerializedName("title_synonyms")
    val titleSynonyms: List<String>? = null,

    @field:SerializedName("source")
    val source: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("duration")
    val duration: String? = null,

    @field:SerializedName("score")
    val score: Double? = null,

    @field:SerializedName("genres")
    val genres: List<GenresResponse>? = null,

    @field:SerializedName("popularity")
    val popularity: Int? = null,

    @field:SerializedName("members")
    val members: Int? = null,

    @field:SerializedName("title_english")
    val titleEnglish: String? = null,

    @field:SerializedName("rank")
    val rank: Int? = null,

    @field:SerializedName("airing")
    val airing: Boolean? = null,

    @field:SerializedName("episodes")
    val episodes: Int? = null,

    @field:SerializedName("aired")
    val aired: AiredResponse? = null,

    @field:SerializedName("studios")
    val studios: List<StudiosResponse>? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("synopsis")
    val synopsis: String? = null,

    @field:SerializedName("licensors")
    val licensors: List<LicensorsResponse>? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("trailer_url")
    val trailerUrl: String? = null,

    @field:SerializedName("producers")
    val producers: List<ProducersResponse>? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class Prop(

    @field:SerializedName("from")
    val from: FromResponse? = null,

    @field:SerializedName("to")
    val to: ToResponse? = null
)

data class LicensorsResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class ProducersResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class ToResponse(

    @field:SerializedName("month")
    val month: Int? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("day")
    val day: Int? = null
)

data class AiredResponse(

    @field:SerializedName("string")
    val string: String? = null,

    @field:SerializedName("prop")
    val prop: Prop? = null,

    @field:SerializedName("from")
    val from: String? = null,

    @field:SerializedName("to")
    val to: String? = null
)

data class StudiosResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class FromResponse(

    @field:SerializedName("month")
    val month: Int? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("day")
    val day: Int? = null
)

data class GenresResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)
