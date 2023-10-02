package com.daves9809.github.core.network.model

data class Repository(
    val createdAt: String,
    val defaultBranchRef: DefaultBranchRef,
    val description: String,
    val issues: Issues,
    val name: String
)