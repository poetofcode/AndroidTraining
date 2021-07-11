package com.example.composeexample02.shimmer

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.shimmer(
    enabled: Boolean = true,
    animation: ShimmerAnimation<Float> = DefaultShimmerAnimation()
): Modifier = composed(
    factory = {
        val density = LocalConfiguration.current.densityDpi
        val widthDp = LocalConfiguration.current.screenWidthDp
        val width = (widthDp / density).toFloat()

        val offsetXAnim = rememberInfiniteTransition()
        val offsetX by offsetXAnim.animateFloat(
            initialValue = 0f,
            targetValue = width,
            animationSpec = animation.animationSpec(0f, width)
        )
        if (!enabled) return@composed this
        this.then(
            Modifier
                .graphicsLayer(alpha = 0.99f)
                .drawWithContent {
                    drawContent()
                    drawRoundRect(
                        brush = animation.brush(offsetX),
                        blendMode = BlendMode.SrcAtop
                    )
                }
        )
    }
)