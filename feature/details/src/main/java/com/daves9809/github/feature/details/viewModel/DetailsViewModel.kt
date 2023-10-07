package com.daves9809.github.feature.details.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daves9809.github.core.common.Launched
import com.daves9809.github.core.common.RequestState
import com.daves9809.github.core.common.stateInMerge
import com.daves9809.github.core.data.repository.GithubRepository
import com.daves9809.github.core.designsystem.UiText
import com.daves9809.github.feature.details.DetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val detailsArgs = DetailsArgs(savedStateHandle)

    init {
        getRepositoryDescription()
    }

    private val _state: MutableStateFlow<DetailsState> = MutableStateFlow(
        DetailsState(
            username = detailsArgs.username,
            repositoryName = detailsArgs.repositoryName
        )
    ).stateInMerge(
        scope = viewModelScope,
        launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
    )

    internal val state: StateFlow<DetailsState> = _state

    private fun getRepositoryDescription() {
        viewModelScope.launch {
            val result = githubRepository.getRepositoryDetails(
                detailsArgs.username,
                detailsArgs.repositoryName
            )
            result
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            requestState = RequestState.Error(
                                UiText.DynamicString(
                                    "${error.message}"
                                )
                            )
                        )
                    }
                }
                .onSuccess { repositoryResult ->
                    with(repositoryResult) {
                        _state.update {
                            it.copy(
                                requestState = RequestState.Success,
                                description = description,
                                issuesCount = issuesCount,
                                commitCount = commitCount,
                                openGraphUrl = openGraphUrl,
                                ownerImageUrl = ownerImageUrl,
                                isPrivate = isPrivate
                            )
                        }
                    }
                }
        }
    }

    fun resetErrorState() {
        _state.update { it.copy(requestState = RequestState.Loading) }
    }
}