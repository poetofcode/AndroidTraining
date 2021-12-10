package com.example.composeexample02.model

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Cat(
    val text: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)

data class ColorItem(
    val title: String,
    val hex: String,
    val value: Color,
    val isFavorite: Boolean = false
)

class FragViewModel : ViewModel() {

    private val _isReady: MutableLiveData<Boolean> = MutableLiveData(false)
    val isReady: LiveData<Boolean> = _isReady

    private val _isContentReady: MutableLiveData<Boolean> = MutableLiveData(false)
    val isContentReady: LiveData<Boolean> = _isContentReady

    init {
        viewModelScope.launch {
            delay(3000L)
            _isReady.postValue(true)

            delay(2000L)
            _isContentReady.postValue(true)
        }
    }
}
