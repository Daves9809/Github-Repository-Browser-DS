package com.daves9809.github.core.network.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.daves9809.github.core.network.RepositoryListQuery
import timber.log.Timber

class GithubPagingSource(
    private val userLogin: String,
    private val apolloClient: ApolloClient
): PagingSource<String, RepositoryListQuery.Node>() {
    private var loadedItems = 0

    override fun getRefreshKey(state: PagingState<String, RepositoryListQuery.Node>): String? =
        null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RepositoryListQuery.Node> {
        if (params is LoadParams.Refresh) loadedItems = 0

        try {
            val userRepositoryList: RepositoryListQuery.Data =
                apolloClient
                    .query(
                        RepositoryListQuery(
                            userLogin = userLogin,
                            first = params.loadSize,
                            after = params.key,
                        )
                    )
                    .execute()
                    .dataAssertNoErrors

            val data = userRepositoryList.user.repositories.edges
            loadedItems += data!!.size
            return LoadResult.Page(
                data = data.map { it!!.node },
                prevKey = null,
                nextKey = data.lastOrNull()?.cursor,
                itemsAfter = userRepositoryList.user.repositories.totalCount - loadedItems,
            )
        } catch (e: Exception) {
            Timber.w(e, "Could not fetch user repository list")
            return LoadResult.Error(e)
        }
    }
}