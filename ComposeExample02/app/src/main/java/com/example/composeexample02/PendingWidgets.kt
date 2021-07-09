package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
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

    @Preview(showSystemUi = true)
    @Composable
    fun Root() {
        val isReady by model.isReady.observeAsState(false)

        CatList(!isReady)
    }

    @Composable
    fun CatList(isLoading: Boolean = false) {
        LazyColumn {
            model.cats.forEach {
                if (isLoading) {
                    items(10) { idx ->
                        PendingCatRow(cat = Cat("fgdghdhhhhghhgh", ""), isLoading = isLoading)
                    }
                } else {
                    item {
                        PendingCatRow(cat = it, isLoading = isLoading)
                    }
                }
            }
        }
    }

    @Composable
    fun PendingCatRow(cat: Cat, isLoading: Boolean) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Pending(
                modifier = Modifier.padding(10.dp).clip(CircleShape),
                isLoading = isLoading
            ) { GlideImage(cat.imageUrl) }
            Pending(
                isLoading = isLoading,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = cat.text,
                    color = Color.Blue,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }

    @Composable
    fun Pending(
        modifier: Modifier = Modifier,
        isLoading: Boolean = true,
        content: @Composable () -> Unit
    ) {
        Box(modifier = modifier) {
            if (!isLoading) {
                content()
            } else {
                Box(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                        // .padding(10.dp)
                        .background(Color.LightGray),
                ) {
                    content()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                    )
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