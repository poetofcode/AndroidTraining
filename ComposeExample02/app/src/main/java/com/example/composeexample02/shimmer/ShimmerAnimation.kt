package com.example.composeexample02.shimmer

import androidx.compose.animation.core.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

interface ShimmerAnimation<T : Number> {
    val colors: List<Color>
    fun animationSpec(from: T, to: T): InfiniteRepeatableSpec<T>
    fun brush(offset: Float): Brush
}

//
// Default implementations
//

class DefaultShimmerAnimation : ShimmerAnimation<Float> {

    override val colors: List<Color>
        get() = listOf(
            Color.LightGray.copy(0.9f),
            Color.LightGray.copy(0.2f),
            Color.LightGray.copy(0.9f)
        )

    override fun animationSpec(from: Float, to: Float): InfiniteRepeatableSpec<Float> =
        infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )

    override fun brush(offset: Float) = Brush.linearGradient(
        colors,
        start = Offset(10f, 00f),
        end = Offset(offset + 10f, offset)
    )

}