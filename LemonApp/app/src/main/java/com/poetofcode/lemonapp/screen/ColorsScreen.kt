package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun Preview() {
    ColorsScreen()
}

private val samples: List<@Composable () -> Unit> = listOf(
    { AlphaColorSample() },
    { Text(text = "TODO") }
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
    Text(text = "Alpha sample")
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