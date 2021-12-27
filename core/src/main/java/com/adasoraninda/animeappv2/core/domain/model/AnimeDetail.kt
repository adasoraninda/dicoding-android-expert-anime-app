package com.adasoraninda.animeappv2.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeDetail(
    val titleJapanese: String,
    val favorites: Int,
    val broadcast: String,
    val rating: String,
    val scoredBy: Int,
    val premiered: String,
    val titleSynonyms: String,
    val source: String,
    val title: String,
    val type: String,
    val duration: String,
    val score: Double,
    val genres: String,
    val popularity: Int,
    val members: Int,
    val titleEnglish: String,
    val rank: Int,
    val airing: Boolean,
    val episodes: Int,
    val aired: String,
    val studios: String,
    val imageUrl: String,
    val malId: Int,
    val synopsis: String,
    val licensors: String,
    val url: String,
    val trailerUrl: String,
    val producers: String,
    val status: String,
    val isFavorite: Boolean = false
) : Parcelable