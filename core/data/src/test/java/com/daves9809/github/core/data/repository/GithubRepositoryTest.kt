package com.daves9809.github.core.data.repository

import com.daves9809.github.core.data.FakeGithubDataSource
import com.daves9809.github.core.data.common.Constants.getRepositoryDetailsException
import com.daves9809.github.core.data.common.Constants.existingRepositoryName
import com.daves9809.github.core.data.common.Constants.existingUserLogin
import com.daves9809.github.core.data.common.Constants.getRepositoryDetailsResponse
import com.daves9809.github.core.model.remote.repositoryDetails.RepositoryDetailsItem
import com.daves9809.github.core.network.dataSource.GithubDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GithubRepositoryTest {
    val fakeGithubDataSource: GithubDataSource = FakeGithubDataSource()
    val sut = GithubRepositoryImpl(fakeGithubDataSource)

    //TODO: unit tests for Paging

    @Test
    fun `getRepositoryDetails, when username and repository name match, returns success`() =
        runTest {
            val username = existingUserLogin
            val repositoryName = existingRepositoryName

            val result = sut.getRepositoryDetails(username, repositoryName)

            assertEquals(Result.success(getRepositoryDetailsResponse), result)
        }

    @Test
    fun `getRepositoryDetails, when username doesn't match, returns failure`() = runTest{
        val username = "nonexistentUser"
        val repositoryName = existingRepositoryName

        val result = sut.getRepositoryDetails(username, repositoryName)

        assertEquals(Result.failure<RepositoryDetailsItem>(getRepositoryDetailsException), result)
    }

    @Test
    fun `getRepositoryDetails, when repository name doesn't match, returns failure`() = runTest{
        val username = existingUserLogin
        val repositoryName = "nonexistentRepo"

        val result = sut.getRepositoryDetails(username, repositoryName)

        assertEquals(Result.failure<RepositoryDetailsItem>(getRepositoryDetailsException), result)
    }

    @Test
    fun `getRepositoryDetails, when username and repository name doesn't match, returns failure`() = runTest{
        val username = "nonexistentUser"
        val repositoryName = "nonexistentRepo"

        val result = sut.getRepositoryDetails(username, repositoryName)

        assertEquals(Result.failure<RepositoryDetailsItem>(getRepositoryDetailsException), result)
    }
}