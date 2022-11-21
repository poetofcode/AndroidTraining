package com.example.composeexample02.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.util.*

@Composable
fun SwipeCalendar(
    modifier: Modifier = Modifier,
    state: CalendarState = CalendarState.DEFAULT,
    dataProvider: CalendarProvider = CalendarProviderImpl(),
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

@Stable
class CalendarState(startYear: Int = -1, startMonth: Int = -1, startDate: Date? = Date()) {

    var selectedMonth: Int by mutableStateOf(startMonth)
        private set

    var selectedYear: Int by mutableStateOf(startYear)
        private set

    var selectedDate: Date? by mutableStateOf(startDate)
        private set

    init {
        if (startDate == null) {
            swipeToMonth(startMonth, startYear)
        } else {
            selectDate(startDate)
        }
    }

    fun selectDate(date: Date) {
        val cal = Calendar.getInstance().apply { time = date }
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        swipeToMonth(month, year)
        selectedDate = cal.time
    }

    fun unselectDate() {
        selectedDate = null
    }

    fun swipeToMonth(monthIndex: Int, year: Int) {
        val cal = Calendar.getInstance().apply {
            if (year > 0) set(Calendar.YEAR, year)
            if (monthIndex > 0) set(Calendar.MONTH, monthIndex)
        }
        this.selectedMonth = cal.get(Calendar.MONTH)
        this.selectedYear = cal.get(Calendar.YEAR)
    }

    fun swipeToNextMonth() {
        if (selectedMonth + 1 > 11) {
            // TODO increment year
            this.selectedMonth = 0
            return
        }
        this.selectedMonth += 1
    }

    fun swipeToPrevMonth() {
        if (selectedMonth - 1 < 0) {
            // TODO decrease year
            this.selectedMonth = 11
            return
        }
        this.selectedMonth -= 1
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
                            val day = dataProvider.getDay(
                                rowIndex,
                                columnIndex,
                                selectedMonth,
                                selectedYear
                            )
                            content(day.copy(selected = isDateEquals(day.date, state.selectedDate)))
                        }
                    }
                }
            }
        }
    }
}

private fun isDateEquals(first: Date?, second: Date?) : Boolean {
    println("mylog isDateEquals: $first, $second")
    if (first == null || second == null) {
        return false
    }
    val firstCal = Calendar.getInstance().apply { time = first }
    val secondCal = Calendar.getInstance().apply { time = second }
    return firstCal[Calendar.YEAR] == secondCal[Calendar.YEAR]
            && firstCal[Calendar.MONTH] == secondCal[Calendar.MONTH]
            && firstCal[Calendar.DAY_OF_MONTH] == secondCal[Calendar.DAY_OF_MONTH]
}

interface SwipeCalendarScope {

    fun item(content: @Composable ColumnScope.() -> Unit)

    fun days(content: @Composable BoxScope.(CalendarDay) -> Unit)

}