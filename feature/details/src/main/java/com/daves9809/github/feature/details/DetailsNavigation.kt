package com.daves9809.github.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.daves9809.github.core.model.navigation.RepositoryDetailsNavArgs
import com.daves9809.github.feature.details.ui.DetailsRoute

const val detailsNavigationRoute = "details_route"
internal const val usernameParameter = "usernameParameter"
internal const val repositoryNameParameter = "repositoryNameParameter"

data class DetailsArgs(val username: String, val repositoryName: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle[usernameParameter]),
        checkNotNull(savedStateHandle[repositoryNameParameter])
    )
}

fun NavController.navigateToDetails(
    repositoryDetailsNavArgs: RepositoryDetailsNavArgs,
    navOptions: NavOptions? = null
) {
    val username = repositoryDetailsNavArgs.username
    val repositoryName = repositoryDetailsNavArgs.repositoryName
    this.navigate("$detailsNavigationRoute/$username/$repositoryName", navOptions)
}

fun NavGraphBuilder.detailsRoute(navigateBack: () -> Unit) {
    composable(route = "$detailsNavigationRoute/{$usernameParameter}/{$repositoryNameParameter}",
        arguments = listOf(
            navArgument(usernameParameter) { type = NavType.StringType },
            navArgument(repositoryNameParameter) { type = NavType.StringType }
        )
    ) {
        DetailsRoute(navigateBack = navigateBack)
    }
}