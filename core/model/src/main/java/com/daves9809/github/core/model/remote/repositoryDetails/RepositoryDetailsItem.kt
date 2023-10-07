package com.daves9809.github.core.model.remote.repositoryDetails

data class RepositoryDetailsItem(
    val name: String = "",
    val createdAt: String = "",
    val description: String = "",
    val issuesCount: Int = -1,
    val commitCount: Int = -1,
    val openGraphUrl: String = "",
    val ownerImageUrl: String = "",
    val isPrivate: Boolean = false
)