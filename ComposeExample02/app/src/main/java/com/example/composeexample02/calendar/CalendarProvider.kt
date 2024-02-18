package com.example.composeexample02.calendar

import java.util.*

interface CalendarProvider {

    fun getRowCount(monthIndex: Int, year: Int): Int

    fun getColumnCount(monthIndex: Int, year: Int): Int

    fun getDay(rowIndex: Int, columnIndex: Int, monthIndex: Int, year: Int): CalendarDay
}


class CalendarProviderImpl: CalendarProvider {

    override fun getRowCount(monthIndex: Int, year: Int): Int {
        return 6
    }

    override fun getColumnCount(monthIndex: Int, year: Int): Int {
        return 7
    }

    override fun getDay(rowIndex: Int, columnIndex: Int, monthIndex: Int, year: Int): CalendarDay {
        var isCurrentMonth = true
        var title = ""

        val weekIndexOfStart = getWeekIndexOfMonthStart(monthIndex, year)
        val allDaysCount = allDaysCount(monthIndex, year)

        val day = (columnIndex - weekIndexOfStart) + (rowIndex * getColumnCount(monthIndex, year)) + 1
        title = day.toString()

        if (day < 1 || day > allDaysCount) {
            isCurrentMonth = false
            title = ""
        }
        return CalendarDay(
            title = title,
            isCurrentMonth = isCurrentMonth,
            date = if (isCurrentMonth) createDate(year, monthIndex, day) else null
        )
    }

    private fun createDate(year: Int, month: Int, day: Int) : Date {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        return cal.time
    }

    private fun getWeekIndexOfMonthStart(monthIndex: Int, year: Int): Int {
        return cal(monthIndex, year).apply {
            set(Calendar.DAY_OF_MONTH, 1)
        }.get(Calendar.DAY_OF_WEEK).run {
            when (val src = this) {
                1 -> 6
                else -> src - 2
            }
        }
    }

    private fun allDaysCount(monthIndex: Int, year: Int) : Int {
        return cal(monthIndex, year).getActualMaximum(Calendar.DAY_OF_MONTH)
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
    val isCurrentMonth: Boolean,
    val selected: Boolean = false,
    val date: Date? = null
)