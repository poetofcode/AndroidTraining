package com.poetofcode.lemonapp.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.poetofcode.lemonapp.ui.theme.veryLarge

@Composable
fun RoundSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(modifier = modifier, content = content, shape = MaterialTheme.shapes.veryLarge)
}