package com.example.composeexample02

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.composeexample02.entity.FakeData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import java.lang.Math.abs
import kotlin.math.absoluteValue

class SliderFragment : Fragment() {

    val spacingState: MutableState<Float> = mutableStateOf(0f)

    @ExperimentalPagerApi
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

    @SuppressLint("RestrictedApi")
    @ExperimentalPagerApi
    @Preview
    @Composable
    fun Root() {
        val images = FakeData.IMAGES
        val pagerState = rememberPagerState(
            pageCount = images.size,
            infiniteLoop = true
        )

        // val isDragEnabled = remember { mutableStateOf(false) }


        val current = pagerState.currentPage
        val target = pagerState.targetPage ?: current

        println("mylog spacing: ${spacingState.value}, curr: $current, target: $target")

        Box(Modifier.background(Color.Black)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                dragEnabled = true,
                itemSpacing = spacingState.value.dp
            ) { pageIdx ->

                val pageOffset = calculateCurrentOffsetForPage(pageIdx).absoluteValue

                ZoomableImage(
                    imageUrl = images[pageIdx],
                    isActive = pagerState.currentPage == pageIdx
                )

            }
        }
    }

    @Composable
    fun ZoomableImage(
        imageUrl: String?,
        maxScale: Float = 4f,
        minScale: Float = 0.7f,
        isActive: Boolean
    ) {
        var scale = remember { mutableStateOf(1f) }
        var bitmap = remember { mutableStateOf<Bitmap?>(null) }
        var offsetX = remember { mutableStateOf(0f) }
        var offsetY = remember { mutableStateOf(0f) }

        // spacingState.value = kotlin.math.abs(offsetX.value)

        val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
        val calcOffset = (screenWidth * scale.value - screenWidth).run {
            if (this < 0) 0f else this
        }

        // println("mylog offset: $calcOffset, screenWidth (dp): $screenWidth")


//        fun calculateNewScale(k: Float): Float =
//            if ((scale.value <= maxScale && k > 1f) || (scale.value >= minScale && k < 1f))
//                scale.value * k
//            else
//                scale.value

        spacingState.value =  with(LocalDensity.current) { calcOffset.toDp() }.value / 2

        Glide.with(LocalContext.current).asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    bitmap.value = resource
                }
            })

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .zIndex(if (isActive) 10f else 1f)
                .background(Color.Black)
                .pointerInput(Unit) {
//                    detectDragGestures { change, dragAmount ->
//                        // val offset = event.calculatePan()
//                        offsetX.value += dragAmount.x
//                        offsetY.value += dragAmount.y
//                    }

                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()

                            println("mylog DRAG START")

                            do {
                                val event = awaitPointerEvent()
                                scale.value *= event.calculateZoom()
                                val offset = event.calculatePan()
                                offsetX.value += offset.x
                                offsetY.value += if (scale.value <= 1f) 0f else offset.y
                            } while (event.changes.any { it.pressed })

                            println("mylog DRAG CANCEL")

                            scale.value = 1f
                            offsetX.value = 0f
                            offsetY.value = 0f
                        }
                    }
                }
        ) {
            bitmap.value?.let { loadedBitmap ->

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale.value,
                            scaleY = scale.value,
//                            translationX = offsetX.value,
                            translationY = offsetY.value
                        ),
                    bitmap = loadedBitmap.asImageBitmap(), // loadedBitmap.asImageBitmap()
                    contentDescription = null

                )
            } ?: kotlin.run {
                Text("Loading Image...", color = Color.White)
            }
        }
    }

}