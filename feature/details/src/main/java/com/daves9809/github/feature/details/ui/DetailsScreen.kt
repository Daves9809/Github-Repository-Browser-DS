package com.daves9809.github.feature.details.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daves9809.github.core.designsystem.AppScreen
import com.daves9809.github.feature.details.viewModel.DetailsState
import com.daves9809.github.feature.details.viewModel.DetailsViewModel

@Composable
fun DetailsRoute(
    navigateBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
){

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    DetailsScreen(uiState = uiState)
}

@Composable
fun DetailsScreen(
    uiState: DetailsState
){
    AppScreen {
        Text(text = "Details Screen")
    }
}