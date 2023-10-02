package com.daves9809.github.feature.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.daves9809.github.feature.details.ui.DetailsRoute

const val detailsNavigationRoute = "details_route"

fun NavController.navigateToDetails(navOptions: NavOptions? = null) {
    this.navigate(detailsNavigationRoute, navOptions)
}

fun NavGraphBuilder.detailsRoute(navigateBack: () -> Unit) {
    composable(route = detailsNavigationRoute){
        DetailsRoute(navigateBack = navigateBack)
    }
}