package com.example.composeexample02.calendar

interface CalendarProvider {

    fun getRowCount(monthIndex: Int): Int

    fun getColumnCount(monthIndex: Int): Int

    fun getDay(rowIndex: Int, columnIndex: Int, monthIndex: Int): CalendarDay

}

class MockCalendarProvider : CalendarProvider {
    override fun getRowCount(monthIndex: Int): Int {
        return 5
    }

    override fun getColumnCount(monthIndex: Int): Int {
        return 7
    }

    override fun getDay(rowIndex: Int, columnIndex: Int, monthIndex: Int): CalendarDay {
        var titleNumber = columnIndex + (rowIndex * getColumnCount(monthIndex)) + 1
        var clickable = true
        if (titleNumber > 31) {
            titleNumber -= 31
            clickable = false
        }
        return CalendarDay(title = titleNumber.toString(), clickable = clickable)
    }
}

data class CalendarDay(
    val title: String,
    val clickable: Boolean,
)