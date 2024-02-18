package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.poetofcode.lemonapp.LocalNightMode
import com.poetofcode.lemonapp.NightMode
import com.poetofcode.lemonapp.isNight
import com.poetofcode.lemonapp.mock.MockData
import com.poetofcode.lemonapp.ui.components.*
import com.poetofcode.lemonapp.ui.theme.*
import kotlin.math.roundToInt

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PreviewDay() {
    FontsScreen()
}

private val samples: List<@Composable () -> Unit> = listOf(
    { StyleVariantsSample() },
    { SizeVariantsSample() },
    {
        Text(
            text = MockData.LOREM_IPSUM,
            style = ScreenState.currentTextStyle.value,
            fontSize = ScreenState.currentTextSize.value.sp
        )
    }
)

@Composable
fun FontsScreen(onDayNightSwitchClick: () -> Unit = {}) {
    LemonAppTheme(darkTheme = LocalNightMode.current.isNight) {
        Scaffold(topBar = {
            TopBar(title = "Fonts", onDayNightSwitchClick = { onDayNightSwitchClick() })
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
private fun StyleVariantsSample() {
    val fontSerif = MaterialTheme.typography.body1_serif
    val fontSans = MaterialTheme.typography.body1_sans
    val fontMono = MaterialTheme.typography.body1_mono

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Font style", style = MaterialTheme.typography.h5, modifier = Modifier.fillMaxWidth())
        ButtonGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            state = ScreenState.styleButtonsState,
            colors = ButtonGroupDefaults.colors(
                activeContent = if (LocalNightMode.current.isNight) YellowMain else AlmostBlack,
                inactiveContent = if (LocalNightMode.current.isNight) AlmostBlack else Color.White,
            ),
            onSelect = { selectedIndex ->
                when (selectedIndex) {
                    0 -> ScreenState.currentTextStyle.value = fontSerif
                    1 -> ScreenState.currentTextStyle.value = fontSans
                    2 -> ScreenState.currentTextStyle.value = fontMono
                }
            }
        )
    }
}

@Composable
private fun SizeVariantsSample() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Font size", style = MaterialTheme.typography.h5, modifier = Modifier.fillMaxWidth())
        ButtonGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            state = ScreenState.sizeButtonsState,
            colors = ButtonGroupDefaults.colors(
                activeContent = if (LocalNightMode.current.isNight) YellowMain else AlmostBlack,
                inactiveContent = if (LocalNightMode.current.isNight) AlmostBlack else Color.White,
            ),
            onSelect = { selectedIndex ->
                when (selectedIndex) {
                    /* 12 */ 0 -> ScreenState.currentTextSize.value = 12
                    /* 16 */ 1 -> ScreenState.currentTextSize.value = 16
                    /* 18 */ 2 -> ScreenState.currentTextSize.value = 18
                    /* 20 */ 3 -> ScreenState.currentTextSize.value = 20
                    /* 24 */ 4 -> ScreenState.currentTextSize.value = 24
                }
            }
        )
    }
}


private object ScreenState {
    val sizeButtonsState = mutableStateOf(ButtonGroupState(
        titles = listOf("12px", "16px", "18px", "20px", "24px"),
        activeIndex = 0
    ))

    val styleButtonsState = mutableStateOf(ButtonGroupState(
        titles = listOf("Serif", "Sans", "Mono"),
        activeIndex = 1)
    )

    val currentTextStyle: MutableState<TextStyle> = mutableStateOf(TextStyle())

    val currentTextSize: MutableState<Int> = mutableStateOf(12)
}