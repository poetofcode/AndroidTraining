package com.example.composeexample02.skeleton

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn as _LazyColumn
import androidx.compose.material.ScrollableTabRow as _ScrollableTabRow
import com.google.accompanist.pager.HorizontalPager as _HorizontalPager

typealias PreviewFactory = (@Composable (Int) -> Boolean)

class Skeleton {
    companion object {
        private const val MAX_PREVIEW_ITEM_COUNT = 100

        @Composable
        fun LazyColumn(
            modifier: Modifier = Modifier,
            state: LazyListState = rememberLazyListState(),
            contentPadding: PaddingValues = PaddingValues(0.dp),
            reverseLayout: Boolean = false,
            verticalArrangement: Arrangement.Vertical =
                if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
            horizontalAlignment: Alignment.Horizontal = Alignment.Start,
            flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
            isLoading: Boolean = true,
            preview: PreviewFactory? = null,
            content: LazyListScope.() -> Unit
        ) {
            val scope = rememberCoroutineScope()
            _LazyColumn(
                modifier,
                state,
                contentPadding,
                reverseLayout,
                verticalArrangement,
                horizontalAlignment,
                flingBehavior
            ) {
                if (isLoading) {
                    state.disableScrolling(scope)
                    items(MAX_PREVIEW_ITEM_COUNT) {
                        preview?.invoke(it)?.let { isProceed ->
                            if (!isProceed) return@items
                        }
                    }
                } else {
                    state.reenableScrolling(scope)
                    content()
                }
            }
        }

        @ExperimentalMaterialApi
        @Composable
        fun TabLazyColumn(
            modifier: Modifier = Modifier,
            state: LazyListState = rememberLazyListState(),
            contentPadding: PaddingValues = PaddingValues(0.dp),
            reverseLayout: Boolean = false,
            verticalArrangement: Arrangement.Vertical =
                if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
            horizontalAlignment: Alignment.Horizontal = Alignment.Start,
            flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
            isLoading: Boolean = true,
            preview: PreviewFactory? = null,
            tabs: List<String> = emptyList(),
            selected: Int = 0,
            onSelect: (Int) -> Unit = {},
            tabView: @Composable RowScope.(title: String, isSelected: Boolean) -> Unit = { _, _ -> },
            content: LazyListScope.() -> Unit
        ) {
            val selectedState = rememberSaveable {
                onSelect(selected)
                mutableStateOf(selected)
            }

            LazyColumn(
                modifier,
                state,
                contentPadding,
                reverseLayout,
                verticalArrangement,
                horizontalAlignment,
                flingBehavior,
                isLoading,
                preview
            ) {
                // Tabs
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        tabs.forEachIndexed { idx, title ->
                            Surface(
                                modifier = Modifier.weight(1f / tabs.size),
                                onClick = {
                                    selectedState.value = idx
                                    onSelect(idx)
                                }
                            ) {
                                tabView(title, idx == selectedState.value)
                            }
                        }
                    }
                }
                // Content items
                content()
            }
        }

        @Composable
        fun ScrollableTabRow(
            selectedTabIndex: Int,
            modifier: Modifier = Modifier,
            backgroundColor: Color = MaterialTheme.colors.primarySurface,
            contentColor: Color = contentColorFor(backgroundColor),
            edgePadding: Dp = TabRowDefaults.ScrollableTabRowPadding,
            indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            divider: @Composable () -> Unit = @Composable {
                TabRowDefaults.Divider()
            },
            isLoading: Boolean = true,
            preview: PreviewFactory? = null,
            tabs: @Composable () -> Unit
        ) {
            _ScrollableTabRow(
                selectedTabIndex,
                modifier,
                backgroundColor,
                contentColor,
                edgePadding,
                indicator,
                divider
            ) {
                if (isLoading) {
                    (0..MAX_PREVIEW_ITEM_COUNT).forEach {
                        preview?.invoke(it)?.let { isProceed ->
                            if (!isProceed) return@_ScrollableTabRow
                        }
                    }
                } else {
                    tabs()
                }
            }
        }

        @ExperimentalPagerApi
        @Composable
        fun HorizontalPager(
            count: Int,
            state: PagerState,
            modifier: Modifier = Modifier,
            reverseLayout: Boolean = false,
            itemSpacing: Dp = 0.dp,
            dragEnabled: Boolean = true,
            flingBehavior: FlingBehavior = PagerDefaults.flingBehavior(state),
            verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
            horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
            isLoading: Boolean = true,
            preview: PreviewFactory? = null,
            content: @Composable PagerScope.(page: Int) -> Unit
        ) {
            _HorizontalPager(
                count,
                modifier,
                state,
                reverseLayout,
                itemSpacing,
                flingBehavior,
                verticalAlignment,
                //horizontalAlignment
                //!isLoading,
            ) { pageIndex ->
                if (isLoading) {
                    preview?.invoke(pageIndex)
                } else {
                    content(pageIndex)
                }
            }
        }

        private fun LazyListState.disableScrolling(scope: CoroutineScope) {
            scope.launch {
                scroll(scrollPriority = MutatePriority.PreventUserInput) {
                    // Await indefinitely, blocking scrolls
                    awaitCancellation()
                }
            }
        }

        private fun LazyListState.reenableScrolling(scope: CoroutineScope) {
            scope.launch {
                scroll(scrollPriority = MutatePriority.PreventUserInput) {
                    // Do nothing, just cancel the previous indefinite "scroll"
                }
            }
        }
    }

}

//
// Extension modifiers
//

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.preview(
    enabled: Boolean = true,
    color: Color = Color(0xFFEEEEEE),
    cornerRadius: CornerRadius = CornerRadius.Zero
): Modifier = composed(
    factory = {
        if (!enabled) return@composed this
        this.then(
            Modifier
                .drawWithContent {
                    drawRoundRect(
                        brush = SolidColor(color),
                        cornerRadius = cornerRadius,
                    )
                }
        )
    }
)