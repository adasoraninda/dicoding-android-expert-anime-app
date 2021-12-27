package com.adasoraninda.animeappv2.core.data.source.remote.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.adasoraninda.animeappv2.core.data.source.remote.network.ApiService
import com.adasoraninda.animeappv2.core.data.source.remote.response.CharactersResponse

class CharacterAnimeRemotePagingSource(
    private val id: Int?,
    private val apiService: ApiService
) : PagingSource<Int, CharactersResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResponse> {
        return try {
            val apiResponse = apiService.getDetailAnimeCharacterStaff(id)
            val characterResponse = apiResponse.characters ?: throw IllegalStateException()
            val perPage = LOAD_PER_PAGE

            val before: Int = params.key ?: INITIAL_PER_PAGE
            var after: Int? = before.plus(perPage)

            val listCharacter = mutableListOf<CharactersResponse>()
            val limit = after ?: LOAD_PER_PAGE

            if (characterResponse.size < limit) {
                val subList = characterResponse.subList(before, limit)
                listCharacter.addAll(subList)
            } else {
                listCharacter.addAll(characterResponse)
                after = null
            }

            LoadResult.Page(
                data = listCharacter,
                prevKey = after,
                nextKey = after?.plus(perPage),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharactersResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LOAD_PER_PAGE) ?: anchorPage?.nextKey?.minus(LOAD_PER_PAGE)
        }
    }

    companion object {
        private const val INITIAL_PER_PAGE = 0
        private const val LOAD_PER_PAGE = 5
    }

}