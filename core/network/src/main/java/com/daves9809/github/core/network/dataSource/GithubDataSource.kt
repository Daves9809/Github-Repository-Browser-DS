package com.daves9809.github.core.network.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.daves9809.github.core.network.RepositoryDetailsQuery
import com.daves9809.github.core.network.RepositoryListQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubDataSourceImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : GithubDataSource {
    override fun getRepositoryList(userLogin: String): Flow<PagingData<RepositoryListQuery.Node>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10),
            pagingSourceFactory = {
                GithubPagingSource(userLogin, apolloClient)
            }
        ).flow
    }

    override suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): RepositoryDetailsQuery.Repository? {
        return apolloClient.query(RepositoryDetailsQuery(username, repositoryName))
            .execute()
            .data?.user?.repository
    }

}

interface GithubDataSource {
    fun getRepositoryList(userLogin: String): Flow<PagingData<RepositoryListQuery.Node>>
    suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): RepositoryDetailsQuery.Repository?
}