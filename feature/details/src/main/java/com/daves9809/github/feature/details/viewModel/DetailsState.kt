package com.daves9809.github.feature.details.viewModel

import com.daves9809.github.core.common.RequestState

data class DetailsState(
    val username: String = "",
    val repositoryName: String = "",
    val description: String = "",
    val issuesCount: Int = 0,
    val commitCount: Int = 0,
    val openGraphUrl: String = "",
    val ownerImageUrl: String = "",
    val isPrivate: Boolean = false,
    val requestState: RequestState = RequestState.Loading
)
