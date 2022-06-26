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
    { Text(text = MockData.LOREM_IPSUM, style = ScreenState.currentTextStyle.value, fontSize = 18.sp) }
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
            activeIndex = 1)
        )
    }
    val fontSerif = MaterialTheme.typography.body1_serif
    val fontSans = MaterialTheme.typography.body1_sans
    val fontMono = MaterialTheme.typography.body1_mono

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            state = buttonsState,
            colors = ButtonGroupDefaults.colors(
                activeContent = if (LocalNightMode.current.isNight) YellowMain else AlmostBlack,
                inactiveContent = if (LocalNightMode.current.isNight) AlmostBlack else Color.White,
            ),
            onSelect = { selectedIndex ->
                println("mylog ActiveIndex selected: $selectedIndex")
                when (selectedIndex) {
                    0 -> ScreenState.currentTextStyle.value = fontSerif
                    1 -> ScreenState.currentTextStyle.value = fontSans
                    2 -> ScreenState.currentTextStyle.value = fontMono
                }
            }
        )
    }
}


private object ScreenState {

    val currentTextStyle: MutableState<TextStyle> = mutableStateOf(TextStyle())

}