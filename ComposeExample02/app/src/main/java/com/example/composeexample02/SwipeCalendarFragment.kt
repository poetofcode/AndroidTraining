package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class SwipeCalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Root()
            }
        }
    }

    @Composable
    private fun Root() {
        Column {
            Text(text = "SwipeCalendar fragment")

            Spacer(modifier = Modifier.size(50.dp))

            SwipeCalendar(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                item {
                    Text(text = "Август")
                }

                days {
                    Text(text = "TODO: Дни календаря")
                }
            }

        }
    }
}


@Composable
fun SwipeCalendar(modifier: Modifier = Modifier, content: SwipeCalendarScope.() -> Unit) {
    val scope = SwipeCalendarScopeImpl().apply(content)

    Column(modifier = modifier) {
        val that = this
        scope.children.forEach { it.invoke(that) }
    }
}

class SwipeCalendarScopeImpl(
    val children: MutableList<@Composable ColumnScope.() -> Unit> = mutableListOf()
) : SwipeCalendarScope {

    override fun item(content: @Composable ColumnScope.() -> Unit) {
        children += content
    }

    override fun days(content: @Composable ColumnScope.() -> Unit) {
        children += {
            repeat(5) { rowIdx ->
                Row {
                    repeat(7) { colIdx ->
                        MockDay(text = "${colIdx + (rowIdx * colIdx)}")
                    }
                }
            }
        }

        // children += content
    }
}


@Composable
private fun MockDay(text: String) {
    Text(text = text, modifier = Modifier
        .size(20.dp)
        .border(width = 1.dp, color = Color.Blue))
}

interface SwipeCalendarScope {

    fun item(content: @Composable ColumnScope.() -> Unit)

    fun days(content: @Composable ColumnScope.() -> Unit)

}