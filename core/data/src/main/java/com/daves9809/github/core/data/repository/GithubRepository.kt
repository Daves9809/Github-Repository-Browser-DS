package com.daves9809.github.core.data.repository

import androidx.paging.PagingData
import com.daves9809.github.core.model.remote.repositoryDetails.RepositoryDetailsItem
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import com.daves9809.github.core.network.dataSource.GithubDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubDataSource: GithubDataSource
) : GithubRepository {
    override fun getRepositoryList(username: String): Flow<PagingData<RepositoryListItem>> =
        githubDataSource.getRepositoryList(username)


    override suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): RepositoryDetailsItem =
        githubDataSource.getRepositoryDetails(username,repositoryName)


}

interface GithubRepository {
    fun getRepositoryList(username: String): Flow<PagingData<RepositoryListItem>>

    suspend fun getRepositoryDetails(username: String, repositoryName: String): RepositoryDetailsItem
}