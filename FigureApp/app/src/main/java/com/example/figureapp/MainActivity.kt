package com.example.figureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.figureapp.ui.theme.FigureAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FigureAppTheme {
                val isQuad = remember { mutableStateOf(true) }
                println("mylog isQuad = ${isQuad}")

                Column(Modifier.selectableGroup()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = isQuad.value,
                            onClick = { isQuad.value = true }
                        )
                        Text(text = "Квадрат")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = !isQuad.value,
                            onClick = { isQuad.value = false }
                        )
                        Text(text = "Окружность")
                    }
                    
                    Spacer(modifier = Modifier.size(50.dp))
                    
                    Figures(isQuad = isQuad.value)
                }
            }
        }
    }
}

@Composable
fun Figures(isQuad: Boolean) {
    Row {

    }
}

