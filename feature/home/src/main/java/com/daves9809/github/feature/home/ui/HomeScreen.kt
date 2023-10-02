package com.daves9809.github.feature.home.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daves9809.github.core.designsystem.AppScreen
import com.daves9809.github.feature.home.viewModel.HomeState
import com.daves9809.github.feature.home.viewModel.HomeViewModel


@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState
    )
}
@Composable
fun HomeScreen(
    uiState: HomeState
){
    AppScreen{
        Text(text = "Home Screen")
    }
}