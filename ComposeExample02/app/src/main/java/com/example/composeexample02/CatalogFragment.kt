package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.fragment.app.Fragment
import com.example.composeexample02.shimmer.shimmer
import com.example.composeexample02.skeleton.Skeleton
import com.example.composeexample02.skeleton.preview
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.max

class CatalogFragment : Fragment() {

    @ExperimentalPagerApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Splash()
            }
        }
    }

    @ExperimentalPagerApi
    @Preview(showBackground = true)
    @Composable
    fun Splash() {
        val pages = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven")
        val pagerState = rememberPagerState(pageCount = pages.size)

        Column {

            Skeleton.ScrollableTabRow(
                modifier = Modifier.shimmer(),
                backgroundColor = Color.Transparent,
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                preview = {
                    TabView(text = "fghfghfjf", isLoading = true)
                    it < 3
                },
                isLoading = true
            ) {
                // Add tabs for all of our pages
                pages.forEach { title ->
                    TabView(text = title)
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val bgArr = pages.mapIndexed { index, _ ->
                    listOf(Color.Blue, Color.Green, Color.Magenta)[index % 3]
                }
                Page(num = page, bgColor = bgArr[page])
            }

        }
    }

    @Composable
    private fun TabView(text: String, selected: Boolean = false, isLoading: Boolean = false) {
        Tab(
            text = {
                Text(
                    modifier = Modifier.preview(isLoading),
                    text = text
                )
            },
            selected = selected,
            onClick = { /* TODO */ },
        )
    }


    @Composable
    fun Indicator(
        modifier: Modifier = Modifier,
        height: Dp = TabRowDefaults.IndicatorHeight,
        color: Color = LocalContentColor.current
    ) {
        Box(
            modifier
                .width(30.dp)
                .height(height)
                .background(color = color)
        )
    }

    @ExperimentalPagerApi
    fun Modifier.myPagerTabIndicatorOffset(
        pagerState: PagerState,
        tabPositions: List<TabPosition>,
    ): Modifier = composed {
        val targetIndicatorOffset: Dp
        val indicatorWidth: Dp

        val currentTab = tabPositions[pagerState.currentPage]
        val targetPage = pagerState.targetPage
        val targetTab = targetPage?.let { tabPositions.getOrNull(it) }

        if (targetTab != null) {
            // The distance between the target and current page. If the pager is animating over many
            // items this could be > 1
            val targetDistance = (targetPage - pagerState.currentPage).absoluteValue
            // Our normalized fraction over the target distance
            val fraction = (pagerState.currentPageOffset / max(targetDistance, 1)).absoluteValue

            targetIndicatorOffset = lerp(currentTab.left, targetTab.left, fraction)
            indicatorWidth = lerp(currentTab.width, targetTab.width, fraction).absoluteValue
        } else {
            // Otherwise we just use the current tab/page
            targetIndicatorOffset = currentTab.left
            indicatorWidth = currentTab.width
        }

        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = targetIndicatorOffset + 30.dp)
            .width(30.dp)
    }

    @Composable
    fun Page(num: Int, bgColor: Color) {
        val typography = MaterialTheme.typography
        Surface(
            color = bgColor,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Page $num", color = Color.White, style = typography.h6)
            }
        }
    }

    private inline val Dp.absoluteValue: Dp
        get() = value.absoluteValue.dp

}