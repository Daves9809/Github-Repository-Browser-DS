package com.daves9809.github.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.daves9809.github.core.model.navigation.RepositoryDetailsNavArgs
import com.daves9809.github.feature.home.ui.HomeRoute

const val homeGraphRoute = "home_graph"
const val homeNavigationRoute = "home_route"

fun NavGraphBuilder.homeGraph(
    navigateToRepositoryDetails: (RepositoryDetailsNavArgs) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = homeGraphRoute,
        startDestination = homeNavigationRoute
    ) {
        composable(route = homeNavigationRoute) {
            HomeRoute(
                navigateToRepositoryDetails = navigateToRepositoryDetails
            )
        }
        nestedGraphs()
    }
}