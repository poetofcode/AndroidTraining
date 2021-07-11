package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.composeexample02.shimmer.shimmer
import com.example.composeexample02.skeleton.Skeleton
import com.example.composeexample02.skeleton.preview
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue
import kotlin.math.max

class CatalogFragment : Fragment() {
    private val model: FragViewModel by activityViewModels()

    @ExperimentalPagerApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CatalogRoot()
            }
        }
    }

    @ExperimentalPagerApi
    @Preview(showBackground = true)
    @Composable
    fun CatalogRoot() {
        val isReady by model.isReady.observeAsState(false)
        val pagerState = rememberPagerState(pageCount = model.tabNames.size)

        Column {

            Skeleton.ScrollableTabRow(
                modifier = Modifier.shimmer(!isReady),
                backgroundColor = Color.Transparent,
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                preview = {
                    TabView(text = "fghfghfjf", isLoading = !isReady)
                    it < 3
                },
                isLoading = !isReady
            ) {
                // Add tabs for all of our pages
                model.tabNames.forEach { title ->
                    TabView(text = title)
                }
            }

            Skeleton.HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                preview = {
                    PageView(0, bgColor = Color.Transparent, isLoading = !isReady)
                    true
                },
                isLoading = !isReady
            ) { pageIndex ->
                PageView(pageIndex = pageIndex, bgColor = Color.Transparent)
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
    fun PageView(pageIndex: Int, bgColor: Color, isLoading: Boolean = false) {
        val typography = MaterialTheme.typography
        val isContentReady by model.isContentReady.observeAsState(false)

        Surface(
            color = bgColor,
            modifier = Modifier.fillMaxSize()
        ) {
            Skeleton.LazyColumn(
                modifier = Modifier.shimmer(!isContentReady),
                isLoading = !isContentReady,
                preview = {
                    CatRow(cat = Cat(text = "dfgfdgdfgfd", imageUrl = ""), isLoading = true)
                    true
                }
            ) {
                model.cats.forEach {
                    item {
                        CatRow(it)
                    }
                }
            }
        }
    }

    @Composable
    fun CatRow(cat: Cat, isLoading: Boolean = false) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxWidth()
                .clickable(
                    onClick = { /* Ignoring onClick */ }
                )
        ) {
            //
            // Cat Avatar
            //
            Image(
                painter = rememberGlidePainter(
                    request = cat.imageUrl,
                    // previewPlaceholder = R.drawable.placeholder
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .preview(isLoading)
            )
            Spacer(Modifier.width(10.dp))
            //
            // Cat Name
            //
            Text(
                text = cat.text,
                color = Color.Blue,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .preview(isLoading)
            )
        }
    }

    private inline val Dp.absoluteValue: Dp
        get() = value.absoluteValue.dp

}