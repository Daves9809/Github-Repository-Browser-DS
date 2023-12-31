package com.daves9809.github.feature.home.viewModel

import androidx.paging.PagingData
import com.daves9809.github.core.common.RequestState
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val username: String = "",
    val usernameHistory: Set<String> = emptySet(),
    val isSearchActive: Boolean = false,
    val repositories: Flow<PagingData<RepositoryListItem>> = emptyFlow(),
    val requestState: RequestState = RequestState.Init
)
