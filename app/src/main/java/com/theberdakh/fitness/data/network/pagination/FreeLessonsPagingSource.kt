package com.theberdakh.fitness.data.network.pagination

/*
class FreeLessonsPagingSource(
    private val networkDataSource: FitnessNetworkDataSource
) : PagingSource<String, Lesson>() {
    override fun getRefreshKey(state: PagingState<String, Lesson>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Lesson> {
        return try {
            when (val result = networkDataSource.getFreeLessons(perPage = 5, cursor = null)) {
                is NetworkResult.Error -> {
                    LoadResult.Error(Exception(result.message))
                }
                is NetworkResult.Success -> {
                    LoadResult.Page(
                        data = result.data..map { it.toDomain() },
                        prevKey = result.data.meta.prevCursor,
                        nextKey = result.data.meta.nextCursor
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}*/
