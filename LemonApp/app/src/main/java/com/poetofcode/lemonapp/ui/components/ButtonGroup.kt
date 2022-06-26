package com.poetofcode.lemonapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun ButtonGroup(
    modifier: Modifier = Modifier,
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
                modifier = Modifier.fillMaxWidth().weight(stateValue.titles.size.toFloat() / 3),
                checked = stateValue.activeIndex == idx,
                onCheckedChange = {
                    state.value = stateValue.copy(
                        activeIndex = idx
                    )
                    onSelect(stateValue.activeIndex)
                }
            ) {
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
                    color = if (stateValue.activeIndex == idx) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.secondary,
                    contentColor = if (stateValue.activeIndex == idx) YellowMain else MaterialTheme.colors.onSecondary
                ) {
                    Box(Modifier.fillMaxWidth()) {
                        Text(text = title, modifier = Modifier.align(Alignment.Center).padding(vertical = 8.dp, horizontal = 16.dp))
                    }
                }
            }
            Spacer(Modifier.size(1.dp))
        }
    }
}