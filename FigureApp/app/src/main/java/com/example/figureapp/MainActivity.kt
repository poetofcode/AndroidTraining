package com.example.figureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

                    Figures(getFactory(isQuad = isQuad.value))
                }
            }
        }
    }

    private fun getFactory(isQuad: Boolean): FigureFactory {
        return if (isQuad) RectangleFactory() else CircleFactory()
    }


}

@Composable
fun Figures(figureFactory: FigureFactory) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        listOf(Color.Blue, Color.Yellow, Color.Green).forEach {
            figureFactory.createFigure().Draw(color = it)
        }
    }
}

abstract class Figure {

    @Composable
    abstract fun Draw(color: Color)
}

class Rectangle : Figure() {

    @Composable
    override fun Draw(color: Color) {
        Box(modifier = Modifier
            .size(100.dp)
            .background(color))
    }
}

class Circle : Figure() {

    @Composable
    override fun Draw(color: Color) {
        Box(modifier = Modifier
            .size(100.dp)
            .background(color = color, shape = CircleShape))
    }
}

// ========================== FACTORY

interface FigureFactory {
    fun createFigure() : Figure
}

class RectangleFactory : FigureFactory {
    override fun createFigure() : Figure {
        return Rectangle()
    }
}

class CircleFactory : FigureFactory {
    override fun createFigure(): Figure {
        return Circle()
    }
}