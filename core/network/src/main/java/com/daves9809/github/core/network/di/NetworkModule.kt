package com.daves9809.github.core.network.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.daves9809.github.core.network.BuildConfig
import com.daves9809.github.core.network.common.Constant
import com.daves9809.github.core.network.common.Constant.HEADER_AUTHORIZATION
import com.daves9809.github.core.network.common.Constant.HEADER_AUTHORIZATION_BEARER
import com.daves9809.github.core.network.dataSource.GithubDataSource
import com.daves9809.github.core.network.dataSource.GithubDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constant.GITHUB_URL)
            .addHttpHeader(
                name = HEADER_AUTHORIZATION,
                value = "$HEADER_AUTHORIZATION_BEARER ${BuildConfig.PAT}"
            )
            .addHttpInterceptor(LoggingInterceptor(LoggingInterceptor.Level.BODY))
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule{

    @Binds
    @Singleton
    fun provideGithubDataSource(githubDataSourceImpl: GithubDataSourceImpl): GithubDataSource
}