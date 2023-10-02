package com.daves9809.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.daves9809.github.ui.common.GithubRepositoryBrowserNavHost
import com.daves9809.github.ui.common.theme.GithubRepositoryBrowserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { GithubRepositoryBrowserTheme { GithubRepositoryBrowserNavHost() } }
    }
}
