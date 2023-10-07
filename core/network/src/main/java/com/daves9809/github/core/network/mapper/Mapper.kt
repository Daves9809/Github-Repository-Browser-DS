package com.daves9809.github.core.network.mapper

import com.daves9809.github.core.model.remote.repositoryDetails.RepositoryDetailsItem
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import com.daves9809.github.core.network.RepositoryDetailsQuery
import com.daves9809.github.core.network.RepositoryListQuery

fun RepositoryDetailsQuery.Repository.toRepositoryDetailsItem() =
    RepositoryDetailsItem(
        name = this.name,
        createdAt = this.createdAt.toString(),
        description = this.description ?: "",
        issuesCount = this.issues.totalCount,
        commitCount = this.defaultBranchRef?.target?.onCommit?.history?.totalCount ?: 0,
        openGraphUrl = this.openGraphImageUrl.toString(),
        ownerImageUrl = this.owner.avatarUrl.toString(),
        isPrivate = this.visibility.toString() == "PRIVATE"
    )

fun RepositoryListQuery.Node.toRepositoryListItem() =
    RepositoryListItem(
        description = this.repositoryFields.description ?: "",
        id = this.repositoryFields.id,
        name = this.repositoryFields.name,
        stargazers = this.repositoryFields.stargazers.totalCount
    )