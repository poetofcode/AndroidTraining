package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
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

            val calendarState = remember {
                mutableStateOf(CalendarState.DEFAULT)
            }

            SwipeCalendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                state = calendarState.value,
            ) {
                item {
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "<")
                        Box(Modifier.weight(1f)) {
                            Text(text = monthNameByIndex(calendarState.value.selectedMonth), modifier = Modifier.align(Alignment.Center))
                        }
                        Text(text = ">")
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(30.dp))
                }

                days {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(width = 1.dp, Color.Cyan)
                    ) {
                        Text(
                            text = it.title,
                            modifier = Modifier.align(Alignment.Center),
                            color = if (it.clickable) Color.Black else Color.LightGray
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
}
