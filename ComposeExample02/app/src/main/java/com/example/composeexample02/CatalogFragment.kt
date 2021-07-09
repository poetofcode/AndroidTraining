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

    fun LazyListState.disableScrolling(scope: CoroutineScope) {
        scope.launch {
            scroll(scrollPriority = MutatePriority.PreventUserInput) {
                // Await indefinitely, blocking scrolls
                awaitCancellation()
            }
        }
    }

    fun LazyListState.reenableScrolling(scope: CoroutineScope) {
        scope.launch {
            scroll(scrollPriority = MutatePriority.PreventUserInput) {
                // Do nothing, just cancel the previous indefinite "scroll"
            }
        }
    }

    @ExperimentalPagerApi
    @Preview(showBackground = true)
    @Composable
    fun Splash() {
        val pages = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven")
        val pagerState = rememberPagerState(pageCount = pages.size)

        val state = rememberLazyListState()
        val scope = rememberCoroutineScope()
        state.disableScrolling(scope)

        Column(
            modifier = Modifier.clickable(enabled = false, onClick = {})
        ) {

            ScrollableTabRow(
                // Our selected tab is our current page
                selectedTabIndex = pagerState.currentPage,
                // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                indicator = { tabPositions ->
                    Indicator(
                        Modifier.myPagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                modifier = Modifier
                    .pointerInput(Unit) {
//                        detectDragGestures { change, dragAmount ->
//                            // change.consumeAllChanges()
//                        }
                        detectTapGestures(
                            onPress = {/* Called when the gesture starts */ },
                            onDoubleTap = { /* Called on Double Tap */ },
                            onLongPress = { /* Called on Long Press */ },
                            onTap = { /* Called on Tap */ }
                        )
                    }
            ) {
                // Add tabs for all of our pages
                pages.forEachIndexed { index, title ->
//                    Tab(
//                        text = { Text(title) },
//                        selected = pagerState.currentPage == index,
//                        onClick = { /* TODO */ }
//                    )

                    Box(modifier = Modifier.padding(10.dp)) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(16.dp)
                                .background(Color.LightGray)
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                dragEnabled = false
            ) { page ->
                val bgArr = pages.mapIndexed { index, _ ->
                    listOf(Color.Blue, Color.Green, Color.Magenta)[index % 3]
                }
                Page(num = page, bgColor = bgArr[page])
            }

        }
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