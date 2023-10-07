package com.daves9809.github.core.data

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.daves9809.github.core.data.common.Constants.existingRepositoryName
import com.daves9809.github.core.data.common.Constants.existingUserLogin
import com.daves9809.github.core.data.common.Constants.existingUserLoginLackOfRepositories
import com.daves9809.github.core.data.common.Constants.getRepositoryDetailsException
import com.daves9809.github.core.data.common.Constants.repositoryListItems
import com.daves9809.github.core.data.common.Constants.getRepositoryDetailsResponse
import com.daves9809.github.core.data.common.Constants.getRepositoryListException
import com.daves9809.github.core.model.remote.repositoryDetails.RepositoryDetailsItem
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import com.daves9809.github.core.network.dataSource.GithubDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGithubDataSource : GithubDataSource {
    override fun getRepositoryList(userLogin: String): Flow<PagingData<RepositoryListItem>> {
        val existingUserResponse = PagingData.from(repositoryListItems)
        val existingUserLackOfRepositoriesResponse = PagingData.empty<RepositoryListItem>()
        val errorResponse =
            PagingData.empty<RepositoryListItem>(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(error = getRepositoryListException),
                    append = LoadState.Error(getRepositoryListException),
                    prepend = LoadState.Error(getRepositoryListException)
                )
            )

        return if (userLogin == existingUserLogin)
            flowOf(existingUserResponse)
        else if(userLogin == existingUserLoginLackOfRepositories)
            flowOf(existingUserLackOfRepositoriesResponse)
        else
            flowOf(errorResponse)
    }

    override suspend fun getRepositoryDetails(
        username: String,
        repositoryName: String
    ): Result<RepositoryDetailsItem> {
        return if (username == existingUserLogin && repositoryName == existingRepositoryName)
            Result.success(getRepositoryDetailsResponse)
        else
            Result.failure(getRepositoryDetailsException)

    }
}