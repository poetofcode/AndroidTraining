package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.background
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
import com.poetofcode.lemonapp.LocalNightMode
import com.poetofcode.lemonapp.NightMode
import com.poetofcode.lemonapp.isNight
import com.poetofcode.lemonapp.ui.components.BorderedBox
import com.poetofcode.lemonapp.ui.components.LemonSliderColors
import com.poetofcode.lemonapp.ui.theme.AlmostBlack
import com.poetofcode.lemonapp.ui.theme.LemonAppTheme
import com.poetofcode.lemonapp.ui.theme.WhiteOfLemonApp
import kotlin.math.roundToInt

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PreviewDay() {
    ColorsScreen()
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PreviewNight() {
    CompositionLocalProvider(LocalNightMode provides NightMode.NIGHT) {
        ColorsScreen()
    }
}

private val samples: List<@Composable () -> Unit> = listOf(
    { AlphaColorSample() },
    // { Text(text = "TODO") }
)

@Composable
fun ColorsScreen(onDayNightSwitchClick: () -> Unit = {}) {
    LemonAppTheme(darkTheme = LocalNightMode.current.isNight) {
        Scaffold(topBar = {
            TopBar(title = "Colors", onDayNightSwitchClick = { onDayNightSwitchClick() })
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
}

@Composable
private fun AlphaColorSample() {
    val sliderPosition = remember { mutableStateOf(0.5f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AlphaCaptionValue(alpha = sliderPosition.value)
        Spacer(Modifier.size(16.dp))
        Slider(
            value = sliderPosition.value,
            colors = LemonSliderColors,
            onValueChange = { sliderPosition.value = it }
        )
    }
}

@Composable
private fun AlphaCaptionValue(alpha: Float) {
    CompositionLocalProvider(LocalContentAlpha provides alpha) {
        Box(Modifier.background(AlmostBlack)) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Alpha: ${(alpha * 100).roundToInt()} %",
                style = MaterialTheme.typography.h5,
                color = WhiteOfLemonApp.copy(alpha = alpha)
            )
        }
    }
}