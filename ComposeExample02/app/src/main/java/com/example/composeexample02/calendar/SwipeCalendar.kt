package com.example.composeexample02.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SwipeCalendar(
    modifier: Modifier = Modifier,
    dataProvider: CalendarProvider = MockCalendarProvider(),
    content: SwipeCalendarScope.() -> Unit)
{
    val scope = SwipeCalendarScopeImpl(dataProvider = dataProvider).apply(content)

    Column(modifier = modifier) {
        val that = this
        scope.children.forEach { it.invoke(that) }
    }
}

class SwipeCalendarScopeImpl(
    val dataProvider: CalendarProvider,
    val children: MutableList<@Composable ColumnScope.() -> Unit> = mutableListOf()
) : SwipeCalendarScope {

    override fun item(content: @Composable ColumnScope.() -> Unit) {
        children += content
    }

    override fun days(content: @Composable BoxScope.(CalendarDay) -> Unit) {
        children += {
            val selectedMonth = 0
            repeat(dataProvider.getRowCount(selectedMonth)) { rowIndex ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(dataProvider.getColumnCount(selectedMonth)) { columnIndex ->
                        Box(modifier = Modifier.weight(1f)) {
                            content(dataProvider.getDay(rowIndex, columnIndex, selectedMonth))
                        }
                    }
                }
            }
        }
    }
}

interface SwipeCalendarScope {

    fun item(content: @Composable ColumnScope.() -> Unit)

    fun days(content: @Composable BoxScope.(CalendarDay) -> Unit)

}