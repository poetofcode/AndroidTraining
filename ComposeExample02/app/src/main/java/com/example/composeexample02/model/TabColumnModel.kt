package com.example.composeexample02.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TabColumnModel : ViewModel() {

    val tabs = listOf("Зерги", "Терраны", "Протосы")

    private val _currentUnits: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val currentUnits: LiveData<List<String>> = _currentUnits

    fun onSelect(idx: Int) {
        Log.d("ComposeApp", "$idx SELECTED")
        _currentUnits.postValue((0..10).map { "${tabs[idx]} $it" })
    }

}