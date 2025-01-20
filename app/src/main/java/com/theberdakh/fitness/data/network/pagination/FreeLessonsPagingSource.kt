package com.theberdakh.fitness.data.network.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.retrofit.FitnessNetworkApi


class FreeLessonsPagingSource(
    private val api: FitnessNetworkApi
) : PagingSource<String, NetworkLesson>() {

    override fun getRefreshKey(state: PagingState<String, NetworkLesson>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, NetworkLesson> {
        return try {
            val response = api.getFreeLessons(params.loadSize, params.key)
            if (response.isSuccessful) {
                return LoadResult.Page(
                    data = response.body()?.data ?: emptyList(),
                    prevKey = response.body()?.meta?.prevCursor ?: "",
                    nextKey = response.body()?.meta?.nextCursor ?: ""
                )
            } else {
                LoadResult.Error(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
