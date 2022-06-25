package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun Preview() {
    ColorsScreen()
}

private val samples: List<@Composable () -> Unit> = listOf(
    { AlphaColorSample() },
    // { Text(text = "TODO") }
)

@Composable
fun ColorsScreen() {
    Scaffold(topBar = {
        TopBar(title = "Colors", onDayNightSwitchClick = {})
    }) { paddings ->
        LazyColumn(Modifier.padding(paddingValues = paddings)) {
            items(samples) {
                BorderedBox {
                    it()
                }
            }
        }
    }
}

@Composable
fun AlphaColorSample() {
    val sliderPosition = remember { mutableStateOf(0.5f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AlphaCaptionValue(alpha = sliderPosition.value)
        Spacer(Modifier.size(16.dp))
        Slider(value = sliderPosition.value, onValueChange = { sliderPosition.value = it })
    }
}

@Composable
fun AlphaCaptionValue(alpha: Float) {
    CompositionLocalProvider(LocalContentAlpha provides alpha) {
        Text(
            text = "Alpha: ${(alpha * 100).roundToInt()} %",
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun BorderedBox(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.large
            ),
    ) {
        Box(Modifier.padding(16.dp), content = content)
    }
}