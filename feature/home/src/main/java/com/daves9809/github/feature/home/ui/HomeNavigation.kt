package com.daves9809.github.feature.home.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute,navOptions)
}

fun NavGraphBuilder.homeGraph(){
    composable(route = homeNavigationRoute){
        HomeRoute()
    }
}