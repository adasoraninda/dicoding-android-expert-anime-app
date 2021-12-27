package com.adasoraninda.animeappv2.core.domain.model

data class AnimeCharacter(
    val role: String,
    val voiceActor: AnimeVoiceActor?,
    val imageUrl: String,
    val name: String,
    val malId: Int,
    val url: String
)

data class AnimeVoiceActor(
    val malId: Int,
    val imageUrl: String,
    val name: String,
    val language: String,
    val url: String,
)
