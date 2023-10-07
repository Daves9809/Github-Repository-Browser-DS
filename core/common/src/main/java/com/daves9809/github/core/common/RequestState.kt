package com.daves9809.github.core.common

import com.daves9809.github.core.designsystem.UiText

sealed interface RequestState{
    data object Init: RequestState
    data object Loading: RequestState
    data object Success: RequestState
    data class Error(val errorMessage: UiText): RequestState
}