package com.example.composeexample02

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.composeexample02.entity.FakeData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

class SliderFragment : Fragment() {

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

    @ExperimentalPagerApi
    @Preview
    @Composable
    fun Root() {
        val images = FakeData.IMAGES
        val pagerState = rememberPagerState(pageCount = images.size)

        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIdx ->
                ZoomableImage(imageUrl = images[pageIdx])
            }
        }
    }

    @Composable
    fun ZoomableImage(imageUrl: String?, maxScale: Float = 4f, minScale: Float = 0.7f) {
        var scale = remember { mutableStateOf(1f) }
        var bitmap = remember { mutableStateOf<Bitmap?>(null) }
        var offsetX = remember { mutableStateOf(0f) }
        var offsetY = remember { mutableStateOf(0f) }

        fun calculateNewScale(k: Float): Float =
            if ((scale.value <= maxScale && k > 1f) || (scale.value >= minScale && k < 1f))
                scale.value * k
            else
                scale.value

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
                .background(Color.Black)
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()
                            do {
                                val event = awaitPointerEvent()
                                scale.value *= event.calculateZoom()
                                val offset = event.calculatePan()
                                offsetX.value += offset.x
                                offsetY.value += offset.y
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
        ) {
            bitmap.value?.let { loadedBitmap ->
                Image(
                    modifier = Modifier
                        .fillMaxSize()
//                        .zoomable(onZoomDelta = {
//                            scale = calculateNewScale(it)
//                        })
//                        .rawDragGestureFilter(
//                            object : DragObserver {
//                                override fun onDrag(dragDistance: Offset): Offset {
//                                    translation = translation.plus(dragDistance)
//                                    return super.onDrag(dragDistance)
//                                }
//                            })

                        .graphicsLayer(
                            scaleX = scale.value,
                            scaleY = scale.value,
                            translationX = offsetX.value,
                            translationY = offsetY.value
                        ),
                    bitmap = loadedBitmap.asImageBitmap(), // loadedBitmap.asImageBitmap()
                    contentDescription = ""

                )
            } ?: kotlin.run {
                Text("Loading Image...", color = Color.White)
            }
        }
    }

}