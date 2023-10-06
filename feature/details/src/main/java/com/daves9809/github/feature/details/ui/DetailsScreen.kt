package com.daves9809.github.feature.details.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.Commit
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.daves9809.github.core.designsystem.AppScreen
import com.daves9809.github.feature.details.R
import com.daves9809.github.feature.details.viewModel.DetailsState
import com.daves9809.github.feature.details.viewModel.DetailsViewModel

@Composable
fun DetailsRoute(
    navigateBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    DetailsScreen(navigateBack = navigateBack, uiState = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navigateBack: () -> Unit,
    uiState: DetailsState
) {
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.details_scren_text_details)) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.details_screen_icon_go_back
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AppScreen(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(state = rememberScrollState())
        ) {
            GraphImage(imageUrl = uiState.openGraphUrl)
            Spacer(modifier = Modifier.padding(16.dp))
            OwnerInfo(
                uiState.username,
                uiState.ownerImageUrl
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TitleAndDescription(uiState.repositoryName, uiState.description)
            Spacer(modifier = Modifier.padding(8.dp))
            PrivateRepository(uiState.isPrivate)
            Spacer(modifier = Modifier.padding(8.dp))
            CommitsAndIssues(uiState.commitCount, uiState.issuesCount)
        }
    }
}

@Composable
private fun CommitsAndIssues(commitCount: Int, issuesCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Commit,
            contentDescription = stringResource(id = R.string.details_screen_icon_commits)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "$commitCount" + " " + stringResource(id = R.string.details_screen_text_commits),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black.copy(0.5f)
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Icon(
            imageVector = Icons.Outlined.ReportProblem,
            contentDescription = stringResource(id = R.string.details_screen_icon_issues)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "$issuesCount" + " " + stringResource(id = R.string.details_screen_text_issues),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black.copy(0.5f)
        )
    }
}

@Composable
private fun PrivateRepository(private: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (private) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = stringResource(id = R.string.details_screen_icon_public)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.details_screen_text_private),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black.copy(0.5f)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Public,
                contentDescription = stringResource(id = R.string.details_screen_icon_public)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.details_screen_text_public),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black.copy(0.5f)
            )
        }
    }
}

@Composable
private fun TitleAndDescription(repositoryName: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = repositoryName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black.copy(0.5f)
        )
    }
}

@Composable
fun OwnerInfo(
    username: String,
    ownerImageUlr: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(24.dp),
            model = ownerImageUlr,
            contentDescription = stringResource(id = R.string.details_screen_icon_avatar)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = username,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black.copy(0.5f)
        )
    }
}

@Composable
private fun GraphImage(imageUrl: String) {
    SubcomposeAsyncImage(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(8.dp))
        .border(
            border = BorderStroke(
                2.dp,
                Color.Black
            ),
            shape = RoundedCornerShape(8.dp)
        ),
        model = imageUrl,
        contentDescription = null,
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        })
}

@Preview
@Composable
fun previewDetailsScreen() {
    AppScreen {
        DetailsScreen(
            {},
            uiState = DetailsState(
                username = "Daves9809",
                repositoryName = "Github Browser",
                description = "Mock description",
                issuesCount = 42,
                commitCount = 239,
                openGraphUrl = "",
                ownerImageUrl = "https://avatars.githubusercontent.com/u/64690389?u=7b31c70f09d76fa02176e3db580545aa45143e15&v=4"
            )
        )
    }
}