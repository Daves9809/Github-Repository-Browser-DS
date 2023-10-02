package com.daves9809.github.feature.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daves9809.github.core.common.Launched
import com.daves9809.github.core.common.stateInMerge
import com.daves9809.github.feature.home.viewModel.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )
    internal val state: StateFlow<HomeState> = _state
}