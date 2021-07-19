package com.example.composeexample02.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TabColumnModel : ViewModel() {

    val tabs = listOf("Зерги", "Терраны", "Протосы")

    private val _currentUnits: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val currentUnits: LiveData<List<String>> = _currentUnits

    private val _selected: MutableLiveData<Int> = MutableLiveData(0)
    val selected: LiveData<Int> = _selected

}