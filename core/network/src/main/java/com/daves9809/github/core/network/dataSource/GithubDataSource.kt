package com.daves9809.github.core.network.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.create
import com.daves9809.github.core.model.remote.repositoryDetails.RepositoryDetailsItem
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import com.daves9809.github.core.network.RepositoryDetailsQuery
import com.daves9809.github.core.network.RepositoryListQuery
import com.daves9809.github.core.network.mapper.toRepositoryDetailsItem
import com.daves9809.github.core.network.mapper.toRepositoryListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class GithubDataSourceImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : GithubDataSource {
    override fun getRepositoryList(userLogin: String): Flow<PagingData<RepositoryListItem>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10),
            pagingSourceFactory = {
                GithubPagingSource(userLogin, apolloClient)
            }
        ).flow
            .map { data ->
                data.map { item ->
                    item.toRepositoryListItem()
                }
            }
    }

    override suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): Result<RepositoryDetailsItem> {

        return try {
            val query = apolloClient.query(RepositoryDetailsQuery(username, repositoryName))
                .execute()
            val repository = query.data?.user?.repository

            Result.success(repository?.toRepositoryDetailsItem() ?: RepositoryDetailsItem())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}

interface GithubDataSource {
    fun getRepositoryList(userLogin: String): Flow<PagingData<RepositoryListItem>>
    suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): Result<RepositoryDetailsItem>
}