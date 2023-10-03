package com.daves9809.github.core.model.remote.repositoryDetails

data class RepositoryDetailsItem(
    val name: String,
    val createdAt: String,
    val description: String,
    val issuesCount: Int,
    val commitCount: Int
)