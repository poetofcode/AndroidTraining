package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.composeexample02.calendar.SwipeCalendar

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

            SwipeCalendar(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                item {
                    Text(text = "Август")
                }

                days {
                    Text(text = "TODO: Дни календаря")
                }
            }

        }
    }
}
