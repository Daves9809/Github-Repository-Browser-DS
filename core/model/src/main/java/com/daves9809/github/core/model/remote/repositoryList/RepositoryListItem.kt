package com.daves9809.github.core.model.remote.repositoryList

data class RepositoryListItem(
    val description: String,
    val id: String,
    val name: String,
    val stargazers: Int
)