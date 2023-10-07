package com.daves9809.github.core.data.common

import com.daves9809.github.core.model.remote.repositoryDetails.RepositoryDetailsItem
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem

object Constants {
    val existingUserLogin = "correctUser"
    val existingUserLoginLackOfRepositories = "correctUserLackOfRepositories"
    val existingRepositoryName = "existingRepository"

    val repositoryListItems = listOf(
        RepositoryListItem("desc1", "1", "repo1", 3),
        RepositoryListItem("desc2", "2", "repo2", 2),
        RepositoryListItem("desc3", "3", "repo3", 3)
    )
    val getRepositoryDetailsResponse = RepositoryDetailsItem("desc1", "1", "repo1", 3)
    val getRepositoryDetailsException = Exception("Wrong username or repositoryName")
    val getRepositoryListException = Exception("Wrong userLogin")
}