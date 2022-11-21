package com.example.composeexample02.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SwipeCalendar(
    modifier: Modifier = Modifier,
    state: CalendarState = CalendarState.DEFAULT,
    dataProvider: CalendarProvider = MockCalendarProvider(),
    content: SwipeCalendarScope.() -> Unit)
{
    val scope = SwipeCalendarScopeImpl(
        dataProvider = dataProvider,
        state = state,
    ).apply(content)

    Column(modifier = modifier) {
        val that = this
        scope.children.forEach { it.invoke(that) }
    }
}

class CalendarState {
    var selectedMonth: Int = 0
        private set

    var selectedYear: Int = -1
        private set

    fun selectMonth(monthIndex: Int = 0, year: Int = -1) {
        this.selectedMonth = monthIndex
        this.selectedYear = year
    }

    companion object {
        val DEFAULT = CalendarState()
    }
}

class SwipeCalendarScopeImpl(
    val dataProvider: CalendarProvider,
    val state: CalendarState,
    val children: MutableList<@Composable() (ColumnScope.() -> Unit)> = mutableListOf(),
) : SwipeCalendarScope {

    override fun item(content: @Composable ColumnScope.() -> Unit) {
        children += content
    }

    override fun days(content: @Composable BoxScope.(CalendarDay) -> Unit) {
        children += {
            val selectedMonth = state.selectedMonth
            val selectedYear = state.selectedYear
            repeat(dataProvider.getRowCount(selectedMonth, selectedYear)) { rowIndex ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(dataProvider.getColumnCount(selectedMonth, selectedYear)) { columnIndex ->
                        Box(modifier = Modifier.weight(1f)) {
                            content(dataProvider.getDay(
                                rowIndex,
                                columnIndex,
                                selectedMonth,
                                selectedYear
                            ))
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