package com.adasoraninda.animeappv2.core.domain.usecase

import androidx.paging.PagingData
import com.adasoraninda.animeappv2.core.data.source.Resource
import com.adasoraninda.animeappv2.core.domain.model.Anime
import com.adasoraninda.animeappv2.core.domain.model.AnimeCharacter
import com.adasoraninda.animeappv2.core.domain.model.AnimeDetail
import com.adasoraninda.animeappv2.core.domain.model.AnimeStaff
import com.adasoraninda.animeappv2.core.domain.repository.IAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeInteractor @Inject constructor(
    private val animeRepository: IAnimeRepository
) : AnimeUseCase {

    override fun getAllAnime(): Flow<PagingData<Anime>> =
        animeRepository.getAllAnime()

    override fun getDetailAnime(id: Int?): Flow<Resource<AnimeDetail>> =
        animeRepository.getDetailAnime(id)

    override fun searchAnime(anime: String?): Flow<PagingData<Anime>> =
        animeRepository.searchAnime(anime)

    override fun getFavoriteAnime(): Flow<PagingData<Anime>> =
        animeRepository.getFavoriteAnime()

    override fun getDetailAnimeCharacter(id: Int?): Flow<PagingData<AnimeCharacter>> =
        animeRepository.getDetailAnimeCharacter(id)

    override fun getDetailAnimeStaff(id: Int?): Flow<PagingData<AnimeStaff>> =
        animeRepository.getDetailAnimeStaff(id)

    override fun getAnimeById(id: Int?): Flow<Anime?> =
        animeRepository.getAnimeById(id)

    override suspend fun setFavoriteAnime(anime: Anime) {
        val newAnime = anime.copy(isFavorite = !anime.isFavorite)
        animeRepository.setFavoriteAnime(newAnime)
    }

    override suspend fun deleteAllFavoriteAnime() {
        animeRepository.deleteAllFavoriteAnime()
    }
}