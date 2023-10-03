package com.daves9809.github.core.data.di

import com.daves9809.github.core.data.repository.GithubRepository
import com.daves9809.github.core.data.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun provideGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository
}