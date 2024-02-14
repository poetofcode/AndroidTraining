package com.poetofcode.site2api_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import java.util.Properties

class MainActivity : AppCompatActivity() {

    val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.initAPI(resources.getString(R.string.backendBaseUrl))
        vm.loadFeed()
    }
}