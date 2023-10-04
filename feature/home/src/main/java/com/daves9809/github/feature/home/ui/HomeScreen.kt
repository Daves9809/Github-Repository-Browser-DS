package com.daves9809.github.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daves9809.github.core.designsystem.AppScreen
import com.daves9809.github.feature.home.viewModel.HomeState
import com.daves9809.github.feature.home.viewModel.HomeViewModel
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.daves9809.github.core.designsystem.theme.Dimens
import com.daves9809.github.core.model.navigation.RepositoryDetailsNavArgs
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import com.daves9809.github.feature.home.R
import com.daves9809.github.feature.home.viewModel.RequestState


@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToRepositoryDetails: (RepositoryDetailsNavArgs) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val repositories = uiState.repositories.collectAsLazyPagingItems()

    val screenActions = HomeScreenActions(
        onUsernameTextChange = viewModel::onUsernameTextChange,
        onSearchSelected = viewModel::onSearchSelected,
        onSearchStateChange = viewModel::onSearchStateChange,
        onNavigateToRepositoryDetails = { username, repositoryName ->
            navigateToRepositoryDetails(RepositoryDetailsNavArgs(username, repositoryName))
        }
    )

    HomeScreen(
        uiState = uiState,
        repositories = repositories,
        screenActions = screenActions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeState,
    repositories: LazyPagingItems<RepositoryListItem>,
    screenActions: HomeScreenActions
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (!uiState.isSearchActive)
                CenterAlignedTopAppBar(
                    title = { Text(text = "Repositories") },
                    actions = {
                        IconButton(onClick = { screenActions.onSearchStateChange(true) }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            else
                SearchBar(
                    username = uiState.username,
                    isSearchActive = uiState.isSearchActive,
                    usernameHistory = uiState.usernameHistory,
                    onUsernameTextChange = screenActions.onUsernameTextChange,
                    onSearchSelected = screenActions.onSearchSelected,
                    onSearchStateChange = screenActions.onSearchStateChange
                )
        }
    ) { paddingValues ->
        AppScreen(
            modifier = Modifier.padding(paddingValues)
        ) {
            RepositoryList(repositories,
                onNavigateToRepositoryDetails = { repositoryName ->
                    screenActions.onNavigateToRepositoryDetails(uiState.username, repositoryName)
                })
            if (repositories.loadState.append == LoadState.Loading && uiState.requestState != RequestState.INIT) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun RepositoryList(
    repositories: LazyPagingItems<RepositoryListItem>,
    onNavigateToRepositoryDetails: (String) -> Unit
) {
    LazyColumn {
        items(
            count = repositories.itemCount,
            key = repositories.itemKey { it.id }
        ) { index ->
            val repository = repositories[index]
            RepositoryItem(
                name = repository?.name ?: "",
                description = repository?.description ?: "",
                stargazers = repository?.stargazers ?: 0,
                onNavigateToRepositoryDetails = onNavigateToRepositoryDetails
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    username: String,
    isSearchActive: Boolean,
    usernameHistory: Set<String>,
    onUsernameTextChange: (String) -> Unit,
    onSearchSelected: () -> Unit,
    onSearchStateChange: (Boolean) -> Unit
) {

    SearchBar(
        query = username,
        onQueryChange = onUsernameTextChange,
        onSearch = { onSearchSelected() },
        active = isSearchActive,
        onActiveChange = onSearchStateChange,
        placeholder = { Text(text = stringResource(id = R.string.home_screen_text_search_user)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.home_screen_icon_search)
            )
        },
        trailingIcon = {
            if (isSearchActive)
                Icon(
                    modifier = Modifier.clickable {
                        onUsernameTextChange("")
                        onSearchStateChange(false)
                        onSearchSelected()
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.home_screen_icon_close)
                )
        },
        content = {
            usernameHistory.forEach {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .clickable {
                        onUsernameTextChange(it)
                        onSearchStateChange(false)
                        onSearchSelected()
                    }) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = stringResource(
                            id = R.string.home_screen_icon_history
                        )
                    )
                    Text(text = it)
                }
            }
        }
    )
}
