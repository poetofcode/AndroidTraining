package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

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

    private val ph: (@Composable (Int) -> Unit) = {
        PendingCatRow(cat = Cat(text = "dfgfdgdfgfd", imageUrl = ""), isLoading = true)
    }

    @Composable
    fun CatList(isLoading: Boolean = false) {
        PendingColumn(isLoading = isLoading, placeholder = ph) {
            model.cats.forEach {
                item {
                    PendingCatRow(it)
                }
            }
        }
    }

    @Composable
    fun PendingColumn(
        modifier: Modifier = Modifier,
        state: LazyListState = rememberLazyListState(),
        contentPadding: PaddingValues = PaddingValues(0.dp),
        reverseLayout: Boolean = false,
        verticalArrangement: Arrangement.Vertical =
            if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
        isLoading: Boolean = true,
        placeholder: (@Composable (Int) -> Unit)? = null,
        content: LazyListScope.() -> Unit
    ) {
        val scope = rememberCoroutineScope()

        LazyColumn(
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
                items(100) {
                    placeholder?.invoke(it)
                }
            } else {
                state.reenableScrolling(scope)
                content()
            }
        }
    }

    @Composable
    fun PendingCatRow(cat: Cat, isLoading: Boolean = false) {
        Row(
            modifier = Modifier.padding(10.dp)
                .placeholder()
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
//                    .placeholder(
//                        cornerRadius = CornerRadius(100f, 100f),
//                        enabled = isLoading,
//                        effect = null
//                    )
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
//                    .placeholder(
//                        enabled = isLoading,
//                        effect = null
//                    )
            )
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


private fun Modifier.placeholder(
    enabled: Boolean = true,
    color: Color = Color.LightGray,
    cornerRadius: CornerRadius = CornerRadius.Zero,
    effect: Any? = null
): Modifier = composed(
    factory = {
        val alphaAnim = rememberInfiniteTransition()
        val alpha by alphaAnim.animateFloat(
            initialValue = 0.3f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        // if (!enabled) return@composed this@placeholder
        this.then(
            Modifier
                .graphicsLayer(alpha = 0.99f)
                .drawWithContent {
//                    drawRoundRect(
//                        brush = Brush.linearGradient(listOf(color, color)),
//                        cornerRadius = cornerRadius,
//                        // blendMode = BlendMode.Multiply
//                    )

                    drawContent()

                    drawRoundRect(
                        //
                        // color = Color.Green,
                        brush = Brush.linearGradient(listOf(Color.Green, Color.Red)),
                        // radius = 100f,
                        blendMode = BlendMode.SrcIn
                    )

                }
        )
    }
)
