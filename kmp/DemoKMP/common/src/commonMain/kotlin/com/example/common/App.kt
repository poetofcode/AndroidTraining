package com.example.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            repeat(50) {
                Button(modifier = Modifier.padding(vertical = 10.dp),
                    onClick = {
                        text = "Hello, ${platformName}"
                    }) {
                    Text(text)
                }
            }
        }
    }
}
