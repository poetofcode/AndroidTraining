package com.poetofcode.lemonapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poetofcode.lemonapp.LocalNightMode
import com.poetofcode.lemonapp.NightMode
import com.poetofcode.lemonapp.R
import com.poetofcode.lemonapp.isNight

@Composable
@Preview(showBackground = true)
private fun Preview() {
    TopBar("Preview Screen") {}
}

@Composable
fun TopBar(title: String, onDayNightSwitchClick: (isNight: Boolean) -> Unit) {
    val textTitle: @Composable () -> Unit = { Text(text = title) }
    val isNight = LocalNightMode.current.isNight
    val iconResId = if (isNight) R.drawable.ic_baseline_wb_sunny_24 else R.drawable.ic_baseline_dark_mode_24
    TopAppBar(
        title = textTitle,
        modifier = Modifier,
        actions = {
            IconButton(onClick = { onDayNightSwitchClick(isNight) }) {
                Icon(painter = painterResource(id = iconResId), contentDescription = null)
            }
        }
    )
}