package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.composeexample02.calendar.CalendarState
import com.example.composeexample02.calendar.SwipeCalendar
import java.text.SimpleDateFormat
import java.util.*

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

            // Calendar 1
            val calendarState by remember {
                mutableStateOf(CalendarState.DEFAULT)
            }
            Calendar(calendarState = calendarState)

            Spacer(modifier = Modifier.size(50.dp))

            // Calendar 2
            val calendarState2 by remember {
                mutableStateOf(CalendarState(
                    startDate = createDate(2022, 11, 31)
                ))
            }
            Calendar(calendarState = calendarState2)
        }
    }

    @Composable
    private fun Calendar(calendarState: CalendarState) {
        SwipeCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            state = calendarState,
        ) {
            item {
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "<", modifier = Modifier.clickable {
                        calendarState.swipeToPrevMonth()
                    })
                    Box(Modifier.weight(1f)) {
                        Text(text = monthNameByIndex(calendarState.selectedMonth), modifier = Modifier.align(Alignment.Center))
                    }
                    Text(text = ">", modifier = Modifier.clickable {
                        calendarState.swipeToNextMonth()
                    })
                }
            }

            item {
                Spacer(modifier = Modifier.size(30.dp))
            }

            days {
                if (it.isCurrentMonth) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .clickable {
                                calendarState.selectDate(it.date ?: return@clickable)
                            }
                            .border(width = 1.dp, Color.Cyan)
                            .background(if (it.selected) Color.Yellow else Color.Transparent)
                    ) {
                        Text(
                            text = it.title,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }

    private fun monthNameByIndex(selectedMonth: Int): String {
        val cal = Calendar.getInstance().apply {
            set(Calendar.MONTH, selectedMonth)
        }
        return SimpleDateFormat("MMMM", Locale.getDefault()).format(cal.time)
    }

    private fun createDate(year: Int, month: Int, day: Int) : Date {
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        return cal.time
    }
}
