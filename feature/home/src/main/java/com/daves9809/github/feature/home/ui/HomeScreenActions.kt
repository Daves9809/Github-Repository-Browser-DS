package com.daves9809.github.feature.home.ui

data class HomeScreenActions(
    val onUsernameTextChange: (String) -> Unit,
    val onSearchSelected: () -> Unit,
    val onSearchStateChange: (Boolean) -> Unit,
    val onNavigateToRepositoryDetails: (String, String) -> Unit
)
