package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

            SwipeCalendar {
                header {
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
        scope.children.forEach { it.invoke() }
    }
}

class SwipeCalendarScopeImpl(
    val children: MutableList<@Composable () -> Unit> = mutableListOf()
) : SwipeCalendarScope {

    override fun header(content: @Composable () -> Unit) {
        children += content
    }

    override fun days(content: @Composable () -> Unit) {
        children += content
    }
}

interface SwipeCalendarScope {

    fun header(content: @Composable () -> Unit)

    fun days(content: @Composable () -> Unit)

}