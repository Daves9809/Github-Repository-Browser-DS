package com.daves9809.github.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daves9809.github.core.designsystem.AppScreen
import com.daves9809.github.feature.home.viewModel.HomeState
import com.daves9809.github.feature.home.viewModel.HomeViewModel
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.daves9809.github.core.designsystem.theme.Dimens
import com.daves9809.github.core.model.remote.repositoryList.RepositoryListItem
import com.daves9809.github.feature.home.R
import com.daves9809.github.feature.home.viewModel.RequestState
import timber.log.Timber


@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val repositories = uiState.repositories.collectAsLazyPagingItems()

    val screenActions = HomeScreenActions(
        onUsernameTextChange = viewModel::onUsernameTextChange,
        onSearchSelected = viewModel::onSearchSelected,
        onSearchStateChange = viewModel::onSearchStateChange
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
    AppScreen {
        SearchBar(
            query = uiState.username,
            onQueryChange = screenActions.onUsernameTextChange,
            onSearch = { screenActions.onSearchSelected() },
            active = uiState.isSearchActive,
            onActiveChange = screenActions.onSearchStateChange,
            placeholder = { Text(text = stringResource(id = R.string.home_screen_text_search_user)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.home_screen_icon_search)
                )
            },
            trailingIcon = {
                if (uiState.isSearchActive)
                    Icon(
                        modifier = Modifier.clickable {
                            screenActions.onUsernameTextChange("")
                            screenActions.onSearchStateChange(false)
                            screenActions.onSearchSelected()
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.home_screen_icon_close)
                    )
            },
            content = {}
        )
        Spacer(modifier = Modifier.height(Dimens.margin))
        LazyColumn {
            items(
                count = repositories.itemCount,
                key = repositories.itemKey { it.id }
            ) { index ->
                val repository = repositories[index]
                RepositoryItem(
                    name = repository?.name ?: "",
                    description = repository?.description ?: "",
                    stargazers = repository?.stargazers ?: 0
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if (repositories.loadState.append == LoadState.Loading && uiState.requestState != RequestState.INIT) {
            CircularProgressIndicator()
        }
    }
}
