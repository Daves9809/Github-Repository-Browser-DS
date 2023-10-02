package com.daves9809.github.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.daves9809.github.feature.home.ui.homeGraph
import com.daves9809.github.feature.home.ui.homeNavigationRoute

@Composable
fun GithubRepositoryBrowserNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = homeNavigationRoute,
    ) {
        homeGraph()
    }
}

