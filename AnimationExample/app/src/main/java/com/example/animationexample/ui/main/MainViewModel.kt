package com.example.animationexample.ui.main

import android.view.View
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val messageText: String = "String from Binding!"

    fun onAnimateButtonClick(v: View) {

        println("mylog On Animate Click !!!")

    }

}