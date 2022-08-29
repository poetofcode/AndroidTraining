package com.example.selfupdatingstateapp

data class TextField(
    val updater: SelfReducer<TextField>,
    val id: Int,
    val text: String,
    val isEnabled: Boolean,
) : SelfReducer<TextField> {
    override fun reduceSelf(cb: TextField.() -> TextField) = updater.reduceSelf(cb)
}

data class UiState(
    val updater: SelfReducer<UiState>,
    val fields: List<TextField>
) : SelfReducer<UiState> {
    override fun reduceSelf(cb: UiState.() -> UiState) = updater.reduceSelf(cb)
}
