package com.daves9809.github.feature.home.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.daves9809.github.core.common.Launched
import com.daves9809.github.core.common.stateInMerge
import com.daves9809.github.core.data.repository.GithubRepository
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import com.daves9809.github.feature.home.viewModel.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )
    internal val state: StateFlow<HomeState> = _state

    fun onUsernameTextChange(username: String) {
        _state.update { it.copy(username = username.trim()) }
    }

    fun onSearchSelected() {
        val usernameQuery = state.value.username
        _state.update {
            it.copy(
                isSearchActive = false,
                repositories = repository.getRepositoryList(username = usernameQuery)
                    .cachedIn(viewModelScope),
                requestState = RequestState.INITIATED
            )
        }
        addUsernameToHistory()
    }

    fun onSearchStateChange(isActive: Boolean) {
        _state.update { it.copy(isSearchActive = isActive) }
    }

    private fun addUsernameToHistory(){
        with(state.value){
            if (username.isNotBlank()){
                _state.update { it.copy(usernameHistory = it.usernameHistory.plus(username)) }
            }
        }
    }
}