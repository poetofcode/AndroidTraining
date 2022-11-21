package com.example.composeexample02.calendar

interface CalendarProvider {

    fun getRowCount(monthIndex: Int): Int

    fun getColumnCount(monthIndex: Int): Int

    fun getDay(rowIndex: Int, columnIndex: Int): CalendarDay

}

class MockCalendarProvider : CalendarProvider {
    override fun getRowCount(monthIndex: Int): Int {
        return 5
    }

    override fun getColumnCount(monthIndex: Int): Int {
        return 7
    }

    override fun getDay(rowIndex: Int, columnIndex: Int): CalendarDay {
        var titleNumber = columnIndex + (rowIndex * columnIndex) + 1
        if (titleNumber > 31) titleNumber -= - 30
        return CalendarDay(title = titleNumber.toString(), clickable = true)
    }
}

data class CalendarDay(
    val title: String,
    val clickable: Boolean,
)