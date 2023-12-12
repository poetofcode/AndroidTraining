package com.example.selfupdatingstateapp

data class TextField(
    val reducer: SelfReducer<TextField>,
    val id: Int,
    val text: String,
    val isEnabled: Boolean,
) : SelfReducer<TextField> by reducer

data class UiState(
    val reducer: SelfReducer<UiState>,
    val fields: List<TextField>
) : SelfReducer<UiState> by reducer
