package com.poetofcode.lemonapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poetofcode.lemonapp.ui.theme.AlmostBlack
import com.poetofcode.lemonapp.ui.theme.YellowMain

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
private fun Preview() {
    ButtonGroup(
        state = mutableStateOf(ButtonGroupState(titles = listOf("First", "Second", "Third"))),
        onSelect = {}
    )
}

data class ButtonGroupState(
    val titles: List<String> = emptyList(),
    val activeIndex: Int = 0,
)

object ButtonGroupDefaults {

    @Composable
    fun colors(
        inactiveBackground: Color = MaterialTheme.colors.secondaryVariant,
        inactiveContent: Color = MaterialTheme.colors.onSecondary,
        activeBackground: Color = MaterialTheme.colors.secondary,
        activeContent: Color = MaterialTheme.colors.onSecondary,
    ): ButtonGroupColors = ButtonGroupColors(
        inactiveBackground = inactiveBackground,
        inactiveContent = inactiveContent,
        activeBackground = activeBackground,
        activeContent = activeContent
    )
}

data class ButtonGroupColors(
    val inactiveBackground: Color,
    val inactiveContent: Color,
    val activeBackground: Color,
    val activeContent: Color,
)

@Composable
fun ButtonGroup(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp, vertical = 10.dp),
    colors: ButtonGroupColors = ButtonGroupDefaults.colors(),
    state: MutableState<ButtonGroupState>,
    onSelect: (activeIndex: Int) -> Unit,
) {
    val stateValue = state.value
    LaunchedEffect(Unit) {
        onSelect(stateValue.activeIndex)
    }
    Row(modifier = modifier) {
        stateValue.titles.forEachIndexed { idx, title ->
            IconToggleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(stateValue.titles.size.toFloat() / 3),
                checked = stateValue.activeIndex == idx,
                onCheckedChange = {
                    state.value = stateValue.copy(
                        activeIndex = idx
                    )
                    onSelect(stateValue.activeIndex)
                }
            ) {
                Button(onClick = { /*TODO*/ }) {

                }

                val cornerRadiusPercent = 30
                val shape = when (idx) {
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadiusPercent,
                        bottomStartPercent = cornerRadiusPercent
                    )
                    (stateValue.titles.size - 1) -> RoundedCornerShape(
                        topEndPercent = cornerRadiusPercent,
                        bottomEndPercent = cornerRadiusPercent
                    )
                    else -> RectangleShape
                }
                Surface(
                    modifier.fillMaxWidth(),
                    shape = shape,
                    color = if (stateValue.activeIndex == idx) {
                        colors.activeBackground
                    } else {
                        colors.inactiveBackground
                    },
                    contentColor = if (stateValue.activeIndex == idx) {
                        colors.activeContent
                    } else {
                        colors.inactiveContent
                    },
                ) {
                    Box(Modifier.fillMaxWidth()) {
                        Text(text = title, maxLines = 1, modifier = Modifier
                            .align(Alignment.Center)
                            .padding(contentPadding)
                        )
                    }
                }
            }
            Spacer(Modifier.size(1.dp))
        }
    }
}