package com.example.composeexample02.skeleton

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn as _LazyColumn
import androidx.compose.material.ScrollableTabRow as _ScrollableTabRow

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