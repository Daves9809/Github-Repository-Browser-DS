package com.daves9809.github.feature.details.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(): ViewModel() {

    private val _state: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState())
    internal val state: StateFlow<DetailsState> = _state
}