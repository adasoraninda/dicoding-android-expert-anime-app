package com.adasoraninda.animeappv2.core.domain.model

data class Anime(
    val malId: Int,
    val title: String,
    val score: Double,
    val imageUrl: String,
    val members: Int,
    val type: String,
    val episodes: Int,
    val isFavorite: Boolean,
)