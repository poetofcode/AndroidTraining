package com.example.composeexample02.calendar

import java.util.*

interface CalendarProvider {

    fun getRowCount(monthIndex: Int, year: Int): Int

    fun getColumnCount(monthIndex: Int, year: Int): Int

    fun getDay(rowIndex: Int, columnIndex: Int, monthIndex: Int, year: Int): CalendarDay

    fun getMonthTitle(monthIndex: Int): String
}

class MockCalendarProvider : CalendarProvider {
    override fun getRowCount(monthIndex: Int, year: Int): Int {
        return 5
    }

    override fun getColumnCount(monthIndex: Int, year: Int): Int {
        return 7
    }

    override fun getDay(rowIndex: Int, columnIndex: Int, monthIndex: Int, year: Int): CalendarDay {
        var titleNumber = columnIndex + (rowIndex * getColumnCount(monthIndex, year)) + 1
        var clickable = true
        if (titleNumber > 31) {
            titleNumber -= 31
            clickable = false
        }
        return CalendarDay(title = titleNumber.toString(), clickable = clickable)
    }

    override fun getMonthTitle(monthIndex: Int): String {
        return "Mock month title"
    }
}

class CalendarProviderImpl: CalendarProvider {
    // val calendar = Calendar.getInstance()

    override fun getRowCount(monthIndex: Int, year: Int): Int {
        return 3
    }

    override fun getColumnCount(monthIndex: Int, year: Int): Int {
        return 7
    }

    override fun getDay(rowIndex: Int, columnIndex: Int, monthIndex: Int, year: Int): CalendarDay {
        return CalendarDay(title = "?", clickable = true)
    }

    override fun getMonthTitle(monthIndex: Int): String {
        return cal(monthIndex).get(Calendar.MONTH).toString()
    }

    private fun cal(monthIndex: Int, year: Int = -1) : Calendar {
        return Calendar.getInstance().apply {
            if (year > 0) set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthIndex)     // 0-based
        }
    }
}

data class CalendarDay(
    val title: String,
    val clickable: Boolean,
)