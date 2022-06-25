package com.poetofcode.lemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poetofcode.lemonapp.screen.MenuScreen
import com.poetofcode.lemonapp.ui.theme.LemonAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEntryPoint()
        }
    }
}

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "menu") {
        composable(route = "menu") {
            MenuScreen()
        }
    }
}