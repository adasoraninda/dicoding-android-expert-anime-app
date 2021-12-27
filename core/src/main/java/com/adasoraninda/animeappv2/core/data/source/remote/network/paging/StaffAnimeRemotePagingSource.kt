package com.adasoraninda.animeappv2.core.data.source.remote.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.adasoraninda.animeappv2.core.data.source.remote.network.ApiService
import com.adasoraninda.animeappv2.core.data.source.remote.response.StaffResponse

class StaffAnimeRemotePagingSource(
    private val id: Int?,
    private val apiService: ApiService
) : PagingSource<Int, StaffResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StaffResponse> {
        return try {
            val apiResponse = apiService.getDetailAnimeCharacterStaff(id)
            val staffResponse = apiResponse.staff ?: throw IllegalStateException()
            val perPage = LOAD_PER_PAGE

            val before: Int = params.key ?: INITIAL_PER_PAGE
            var after: Int? = before.plus(perPage)

            val listStaff = mutableListOf<StaffResponse>()
            val limit = after ?: LOAD_PER_PAGE

            if (staffResponse.size < limit) {
                val subList = staffResponse.subList(before, limit)
                listStaff.addAll(subList)
            } else {
                listStaff.addAll(staffResponse)
                after = null
            }

            LoadResult.Page(
                data = listStaff,
                prevKey = after,
                nextKey = after?.plus(perPage),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StaffResponse>): Int? {
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