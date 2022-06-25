package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
private fun Preview() {
    TopBar("Preview Screen") {}
}

@Composable
fun TopBar(title: String, onDayNightSwitchClick: (isNight: Boolean) -> Unit) {
    val textTitle: @Composable () -> Unit = { Text(text = title) }
    TopAppBar(title = textTitle,
        modifier = Modifier)
}