package com.poetofcode.lemonapp.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable

val LemonSliderColors: SliderColors
    @Composable get() = SliderDefaults.colors(
        activeTickColor = MaterialTheme.colors.secondary,
        activeTrackColor = MaterialTheme.colors.secondary,
        thumbColor = MaterialTheme.colors.secondary
    )