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
import com.poetofcode.lemonapp.LocalNightMode
import com.poetofcode.lemonapp.isNight
import com.poetofcode.lemonapp.ui.components.RoundSurface
import com.poetofcode.lemonapp.ui.theme.LemonAppTheme
import com.poetofcode.lemonapp.ui.theme.veryLarge

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun Preview() {
    MenuScreen()
}

@Composable
fun MenuScreen(
    onDayNightSwitchClick: () -> Unit = {},
    onNavigateToColorsScreen: () -> Unit = {},
    onNavigateToFontsScreen: () -> Unit = {},
) {
    LemonAppTheme(darkTheme = LocalNightMode.current.isNight) {
        Scaffold(topBar = {
            TopBar(title = "Menu", onDayNightSwitchClick = { onDayNightSwitchClick() })
        }) { paddings ->
            Box(Modifier.padding(paddingValues = paddings)) {
                RoundSurface(Modifier.padding(16.dp)) {
                    Column {
                        MenuItem(title = "Colors") {
                            onNavigateToColorsScreen()
                        }

                        MenuItem(title = "Fonts") {
                            onNavigateToFontsScreen()
                        }

                        MenuItem(title = "Lists") {
                            // TODO
                        }
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