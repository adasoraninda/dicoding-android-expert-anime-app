package com.adasoraninda.animeappv2.core.utils

import com.adasoraninda.animeappv2.core.data.source.local.enitty.AnimeEntity
import com.adasoraninda.animeappv2.core.data.source.remote.response.*
import com.adasoraninda.animeappv2.core.domain.model.*
import com.adasoraninda.animeappv2.core.exceptions.IdNullException

object DataMapper {
    private const val DEFAULT_VALUE_STR = "-"
    private const val DEFAULT_VALUE_INT = 0
    private const val DEFAULT_VALUE_ID = -1
    private const val DEFAULT_VALUE_DOUBLE = 0.0
    private const val DEFAULT_VALUE_BOOL = false
    private const val FILTER_ACTOR = "Japanese"

    fun mapResponseToEntity(animeResponse: AnimeResponse?): AnimeEntity {
        return AnimeEntity(
            malId = animeResponse?.malId ?: throw IdNullException(),
            title = animeResponse.title,
            score = animeResponse.score,
            imageUrl = animeResponse.imageUrl,
            members = animeResponse.members,
            type = animeResponse.type,
            episodes = animeResponse.episodes,
        )
    }

    fun mapResponseToDomain(animeResponse: AnimeResponse?): Anime {
        return Anime(
            malId = animeResponse?.malId ?: throw IdNullException(),
            title = animeResponse.title ?: DEFAULT_VALUE_STR,
            score = animeResponse.score ?: DEFAULT_VALUE_DOUBLE,
            imageUrl = animeResponse.imageUrl ?: DEFAULT_VALUE_STR,
            members = animeResponse.members ?: DEFAULT_VALUE_INT,
            type = animeResponse.type ?: DEFAULT_VALUE_STR,
            episodes = animeResponse.episodes ?: DEFAULT_VALUE_INT,
            isFavorite = false
        )
    }

    fun mapEntityToDomain(animeEntity: AnimeEntity?): Anime {
        return Anime(
            malId = animeEntity?.malId ?: DEFAULT_VALUE_ID,
            title = animeEntity?.title ?: DEFAULT_VALUE_STR,
            score = animeEntity?.score ?: DEFAULT_VALUE_DOUBLE,
            imageUrl = animeEntity?.imageUrl ?: DEFAULT_VALUE_STR,
            members = animeEntity?.members ?: DEFAULT_VALUE_INT,
            type = animeEntity?.type ?: DEFAULT_VALUE_STR,
            episodes = animeEntity?.episodes ?: DEFAULT_VALUE_INT,
            isFavorite = animeEntity?.isFavorite ?: false
        )
    }

    fun mapDetailResponseToDomain(animeResponse: DetailAnimeResponse?): AnimeDetail {
        return AnimeDetail(
            malId = animeResponse?.malId ?: throw IdNullException(),
            title = animeResponse.title ?: DEFAULT_VALUE_STR,
            score = animeResponse.score ?: DEFAULT_VALUE_DOUBLE,
            imageUrl = animeResponse.imageUrl ?: DEFAULT_VALUE_STR,
            members = animeResponse.members ?: DEFAULT_VALUE_INT,
            type = animeResponse.type ?: DEFAULT_VALUE_STR,
            episodes = animeResponse.episodes ?: DEFAULT_VALUE_INT,
            broadcast = animeResponse.broadcast ?: DEFAULT_VALUE_STR,
            rating = animeResponse.rating ?: DEFAULT_VALUE_STR,
            scoredBy = animeResponse.scoredBy ?: DEFAULT_VALUE_INT,
            premiered = animeResponse.premiered ?: DEFAULT_VALUE_STR,
            titleSynonyms = mapJoinToString(animeResponse.titleSynonyms?.joinToString()),
            source = animeResponse.source ?: DEFAULT_VALUE_STR,
            duration = animeResponse.duration ?: DEFAULT_VALUE_STR,
            popularity = animeResponse.popularity ?: DEFAULT_VALUE_INT,
            titleEnglish = animeResponse.titleEnglish ?: DEFAULT_VALUE_STR,
            rank = animeResponse.rank ?: DEFAULT_VALUE_INT,
            airing = animeResponse.airing ?: DEFAULT_VALUE_BOOL,
            aired = animeResponse.aired?.string ?: DEFAULT_VALUE_STR,
            synopsis = animeResponse.synopsis ?: DEFAULT_VALUE_STR,
            url = animeResponse.url ?: DEFAULT_VALUE_STR,
            trailerUrl = animeResponse.trailerUrl ?: DEFAULT_VALUE_STR,
            status = animeResponse.status ?: DEFAULT_VALUE_STR,
            titleJapanese = animeResponse.titleJapanese ?: DEFAULT_VALUE_STR,
            favorites = animeResponse.favorites ?: DEFAULT_VALUE_INT,
            genres = mapJoinToString(animeResponse.genres?.joinToString { it.name.toString() }),
            studios = mapJoinToString(animeResponse.studios?.joinToString { it.name.toString() }),
            licensors = mapJoinToString(animeResponse.licensors?.joinToString { it.name.toString() }),
            producers = mapJoinToString(animeResponse.producers?.joinToString { it.name.toString() }),
        )
    }

    fun mapDomainToEntity(anime: Anime): AnimeEntity {
        return AnimeEntity(
            malId = anime.malId,
            title = mapValueEntity(anime.title),
            score = mapValueEntity(anime.score),
            imageUrl = mapValueEntity(anime.imageUrl),
            members = mapValueEntity(anime.members),
            type = mapValueEntity(anime.type),
            episodes = mapValueEntity(anime.episodes),
            isFavorite = anime.isFavorite
        )
    }

    fun mapDetailDomainToItemDomain(detailAnime: AnimeDetail): Anime {
        return Anime(
            malId = detailAnime.malId,
            title = detailAnime.title,
            score = detailAnime.score,
            imageUrl = detailAnime.imageUrl,
            members = detailAnime.members,
            type = detailAnime.type,
            episodes = detailAnime.episodes,
            isFavorite = detailAnime.isFavorite,
        )
    }

    fun mapCharacterResponseToDomain(character: CharactersResponse?): AnimeCharacter {
        return AnimeCharacter(
            malId = character?.malId ?: throw IdNullException(),
            role = character.role ?: DEFAULT_VALUE_STR,
            voiceActor = mapListVoiceActorResponseToDomain(character.voiceActors),
            imageUrl = character.imageUrl ?: DEFAULT_VALUE_STR,
            name = character.name ?: DEFAULT_VALUE_STR,
            url = character.url ?: DEFAULT_VALUE_STR
        )
    }

    private fun mapListVoiceActorResponseToDomain(list: List<VoiceActorsResponse>?): AnimeVoiceActor {
        return list?.filter {
            it.language == FILTER_ACTOR
        }?.map {
            AnimeVoiceActor(
                malId = it.malId ?: throw IdNullException(),
                imageUrl = it.imageUrl ?: DEFAULT_VALUE_STR,
                name = it.name ?: DEFAULT_VALUE_STR,
                language = it.language ?: DEFAULT_VALUE_STR,
                url = it.url ?: DEFAULT_VALUE_STR,
            )
        }?.firstOrNull() ?: AnimeVoiceActor(
            malId = DEFAULT_VALUE_ID,
            imageUrl = DEFAULT_VALUE_STR,
            name = DEFAULT_VALUE_STR,
            language = DEFAULT_VALUE_STR,
            url = DEFAULT_VALUE_STR,
        )
    }

    fun mapStaffResponseToDomain(staff: StaffResponse?): AnimeStaff {
        return AnimeStaff(
            malId = staff?.malId ?: throw IdNullException(),
            imageUrl = staff.imageUrl ?: DEFAULT_VALUE_STR,
            name = staff.name ?: DEFAULT_VALUE_STR,
            positions = mapJoinToString(staff.positions?.joinToString()),
            url = staff.url ?: DEFAULT_VALUE_STR,
        )
    }

    private fun mapJoinToString(str: String?): String {
        return if (str.isNullOrBlank()) DEFAULT_VALUE_STR else str
    }

    private fun <T> mapValueEntity(data: T): T? {
        return when (data) {
            DEFAULT_VALUE_STR -> null
            DEFAULT_VALUE_INT -> null
            DEFAULT_VALUE_DOUBLE -> null
            else -> data
        }
    }

}