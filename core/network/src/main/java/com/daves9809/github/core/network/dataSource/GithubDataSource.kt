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
                    RepositoryListItem(
                        description = item.repositoryFields.description ?: "",
                        id = item.repositoryFields.id,
                        name = item.repositoryFields.name,
                        stargazers = item.repositoryFields.stargazers.totalCount
                    )
                }
            }
    }

    override suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): RepositoryDetailsItem {

        val query =  apolloClient.query(RepositoryDetailsQuery(username, repositoryName))
            .execute()
        val repository = query.data?.user?.repository
        if (query.hasErrors()){
            Timber.e("Error = ${query.errors}")
        }

        return RepositoryDetailsItem(
            createdAt = repository?.createdAt.toString() ?: "",
            description = repository?.description ?: "",
            issuesCount = repository?.issues?.totalCount ?: 0,
            commitCount = repository?.defaultBranchRef?.target?.onCommit?.history?.totalCount ?: 0,
            name = repository?.name ?: "",
            openGraphUrl = repository?.openGraphImageUrl.toString() ?: "",
            ownerImageUrl = repository?.owner?.avatarUrl.toString() ?: "",
            isPrivate = repository?.visibility?.toString().equals("PRIVATE")
        )
    }

}

interface GithubDataSource {
    fun getRepositoryList(userLogin: String): Flow<PagingData<RepositoryListItem>>
    suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): RepositoryDetailsItem
}