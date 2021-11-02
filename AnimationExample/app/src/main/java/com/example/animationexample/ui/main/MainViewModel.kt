package com.example.animationexample.ui.main

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val messageText: String = "String from Binding!"
    val isRotated: ObservableField<Boolean> = ObservableField()

    fun init() {
        isRotated.set(false)
    }

    fun onAnimateButtonClick(v: View) {

        println("mylog On Animate Click !!!")
        isRotated.set(true)

    }

}