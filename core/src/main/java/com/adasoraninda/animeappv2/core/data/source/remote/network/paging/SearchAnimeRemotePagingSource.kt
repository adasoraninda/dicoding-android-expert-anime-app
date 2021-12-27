package com.adasoraninda.animeappv2.core.data.source.remote.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.adasoraninda.animeappv2.core.data.source.remote.network.ApiService
import com.adasoraninda.animeappv2.core.data.source.remote.response.AnimeResponse

class SearchAnimeRemotePagingSource(
    private val query: String?,
    private val apiService: ApiService
) : PagingSource<Int, AnimeResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeResponse> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val limit = 10

            val apiResponse = apiService.searchAnime(query, page, limit)
            val listAnime = apiResponse.results

            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (listAnime.isNullOrEmpty()) null else page + 1

            LoadResult.Page(
                data = listAnime ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey,
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}