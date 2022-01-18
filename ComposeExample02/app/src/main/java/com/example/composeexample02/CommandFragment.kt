package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.composeexample02.entity.FakeData.Companion.COLOR_LIST
import com.example.composeexample02.model.ColorItem
import com.example.composeexample02.model.ColorUIEvent
import com.example.composeexample02.model.FragViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

class CommandFragment : Fragment() {
    private val model: FragViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ColorViewList(COLOR_LIST, model.onEvent)
            }
        }
    }
}

typealias ColorEvents = (ColorUIEvent) -> Unit

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    ColorViewList(
        colors = COLOR_LIST,
        events = {}
    )
}

@Composable
fun ColorViewList(colors: List<ColorItem>, events: ColorEvents) {
    val lazyListState = rememberLazyListState()

    CollapsingToolbarScaffold(
        state = rememberCollapsingToolbarScaffoldState(), // provide the state of the scaffold
        toolbar = {
            TitleBar(modifier = Modifier.fillMaxWidth())
        },
        scrollStrategy = ScrollStrategy.EnterAlways,
        modifier = Modifier
    ) {
        LazyColumn(state = lazyListState) {
            items(items = colors) {
                ColorViewItem(col = it, events = events)
            }
        }
    }
}

@Composable
fun TitleBar(modifier: Modifier) {
    Box(
        modifier = modifier
            .border(2.dp, shape = RectangleShape, color = Color.Cyan)
            .background(color = Color.Cyan)
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // events.invoke(ColorUIEvent.TitleClickEvent())
            }
    ) {
        Text(
            text = "Цвета",
            fontSize = 24.sp,
            color = Color.Blue,
            modifier = Modifier
        )
    }
}

@Composable
fun ColorViewItem(col: ColorItem, events: ColorEvents) {

    Column(
        Modifier
            .padding(10.dp)
            .background(Color.LightGray)
            .fillMaxWidth()
            .clickable {
                events.invoke(ColorUIEvent.ClickEvent(col))
            }) {
        Text(
            text = col.title,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )

        //
        // Color
        //
        Card(
            Modifier
                .fillMaxSize()
                .height(150.dp)
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = col.value
        ) {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = col.hex,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        //
        // Like button
        //
        Text(
            text = "Нравится",
            fontSize = 16.sp,
            color = if (col.isFavorite) Color.Red else Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
                .clickable { events.invoke(ColorUIEvent.LikeEvent(col, !col.isFavorite)) }
        )
    }
}
