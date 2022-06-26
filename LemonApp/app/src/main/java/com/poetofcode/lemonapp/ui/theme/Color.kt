package com.poetofcode.lemonapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.poetofcode.lemonapp.LocalNightMode
import com.poetofcode.lemonapp.isNight

val YellowMain = Color(0xFFFFEB3B)
val YellowDark = Color(0xFFFFC107)

val WhiteOfLemonApp @Composable get() = if (!LocalNightMode.current.isNight) Color(0xFFFFFFFF) else YellowDark

val AlmostBlack = Color(0xFF202020)
val AccentColor = Color(0xFF03DAC5)

val PrimaryDark: Color = Color(0xFF121212)
val PrimaryVariantDark: Color = Color(0xFF121212)

