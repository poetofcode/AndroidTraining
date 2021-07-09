package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.accompanist.glide.rememberGlidePainter

class PendingWidgets : Fragment() {

    private val model: FragViewModel by activityViewModels()

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

    data class Item(
        val text: String,
        val imageUrl: String
    )

    @Preview(showSystemUi = true)
    @Composable
    fun Root() {
        val isReady by model.isReady.observeAsState(false)

        if (isReady) {
            CatList()
        } else {
            LoadingScreen()
        }
    }

    @Composable
    fun CatList() {
        LazyColumn {
            model.cats.forEach {
                item {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        GlideImage(it.imageUrl)
                        Text(
                            text = it.text,
                            color = Color.Blue,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun GlideImage(url: String) {
        Image(
            painter = rememberGlidePainter(
                request = url,
                // previewPlaceholder = R.drawable.placeholder
            ),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .padding(end = 20.dp)
        )
    }

    @Preview
    @Composable
    fun LoadingScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Загрузка...")
        }
    }

}