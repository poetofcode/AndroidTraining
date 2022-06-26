package com.poetofcode.lemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poetofcode.lemonapp.screen.ColorsScreen
import com.poetofcode.lemonapp.screen.FontsScreen
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

enum class NightMode { DAY, NIGHT }
val LocalNightMode = staticCompositionLocalOf { NightMode.DAY }

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    val inSystemInDarkMode = isSystemInDarkTheme()
    val currentNightMode = remember { mutableStateOf(if (inSystemInDarkMode) NightMode.NIGHT else NightMode.DAY) }

    val inverseThemeColorMode: () -> Unit = { currentNightMode.value = currentNightMode.value.inversed() }

    CompositionLocalProvider(LocalNightMode provides currentNightMode.value) {
        NavHost(navController, startDestination = "menu") {
            composable(route = "menu") {
                MenuScreen(
                    onDayNightSwitchClick = inverseThemeColorMode,
                    onNavigateToColorsScreen = { navController.navigate("menu/colors") },
                    onNavigateToFontsScreen = { navController.navigate("menu/fonts") },
                )
            }

            composable(route = "menu/colors") {
                ColorsScreen(onDayNightSwitchClick = inverseThemeColorMode)
            }

            composable(route = "menu/fonts") {
                FontsScreen(onDayNightSwitchClick = inverseThemeColorMode)
            }
        }
    }
}

fun NightMode.inversed() : NightMode {
    return if (isNight) {
        NightMode.DAY
    } else {
        NightMode.NIGHT
    }
}

val NightMode.isNight get() = this == NightMode.NIGHT