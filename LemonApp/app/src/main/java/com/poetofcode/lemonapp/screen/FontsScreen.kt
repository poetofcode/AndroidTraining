package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poetofcode.lemonapp.LocalNightMode
import com.poetofcode.lemonapp.NightMode
import com.poetofcode.lemonapp.isNight
import com.poetofcode.lemonapp.mock.MockData
import com.poetofcode.lemonapp.ui.components.*
import com.poetofcode.lemonapp.ui.theme.AlmostBlack
import com.poetofcode.lemonapp.ui.theme.LemonAppTheme
import com.poetofcode.lemonapp.ui.theme.WhiteOfLemonApp
import com.poetofcode.lemonapp.ui.theme.YellowMain
import kotlin.math.roundToInt

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PreviewDay() {
    FontsScreen()
}

private val samples: List<@Composable () -> Unit> = listOf(
    { StyleVariantsSample() },
    { Text(text = MockData.LOREM_IPSUM) }
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
    val sliderPosition = remember { mutableStateOf(0.5f) }
    val buttonsState = remember {
        mutableStateOf(ButtonGroupState(titles = listOf("Serif", "Sans", "Mono"),
            activeIndex = 0)
        )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            state = buttonsState,
            colors = ButtonGroupDefaults.colors(
                activeContent = if (LocalNightMode.current.isNight) YellowMain else AlmostBlack,
                inactiveContent = if (LocalNightMode.current.isNight) AlmostBlack else Color.White,
            ),
            onSelect = {
                println("mylog ActiveIndex selected: $it")
            }
        )
    }
}


