package com.example.composeexample02

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment


class MainActivity : FragmentActivity() {

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment
        navController = navHostFragment.navController
    }
}