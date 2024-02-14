package com.poetofcode.site2api_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Properties

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("mylog BaseUrl: ${resources.getString(R.string.backendBaseUrl)}")
    }
}