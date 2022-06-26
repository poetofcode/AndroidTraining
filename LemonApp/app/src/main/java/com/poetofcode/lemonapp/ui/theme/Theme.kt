package com.poetofcode.lemonapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = AlmostBlack,
    primaryVariant = PrimaryVariantDark,
    secondary = AccentColor,

    background = PrimaryVariantDark,
    surface = AlmostBlack,
    onPrimary = YellowMain,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = YellowMain,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = YellowDark,
    primaryVariant = YellowMain,
    secondary = AccentColor,

    /* Other default colors to override */
    background = YellowMain,
    surface = YellowDark,
    onPrimary = AlmostBlack,
    onSecondary = AlmostBlack,
    onBackground = AlmostBlack,
    onSurface = AlmostBlack,
)

@Composable
fun LemonAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}