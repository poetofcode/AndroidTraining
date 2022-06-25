package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun Preview() {
    MenuScreen()
}

@Composable
fun MenuScreen(
    onNavigateToColorsScreen: () -> Unit = {}
) {
    Scaffold(topBar = {
        TopBar(title = "Menu", onDayNightSwitchClick = {})
    }) { paddings ->
        Box(Modifier.padding(paddingValues = paddings)) {
            Surface(Modifier.padding(16.dp)) {
                Column {
                    MenuItem(title = "Colors") {
                        onNavigateToColorsScreen()
                    }

                    MenuItem(title = "Fonts") {
                        // TODO
                    }

                    MenuItem(title = "Other") {
                        // TODO
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuItem(title: String, onClick: () -> Unit) {
    Column {
        Text(text = title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 10.dp, horizontal = 16.dp))

        Divider(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp))
    }
}