package com.example.composeexample02.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun GlideImage(
    modifier: Modifier = Modifier,
    request: Any,
    @DrawableRes defaultImageResId: Int = 0,
    @DrawableRes previewPlaceholderResId: Int = 0,
    contentScale: ContentScale = ContentScale.Fit,
    isPreview: Boolean = false
) {
    if (isPreview && defaultImageResId == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
        )
        return
    }

    if (isPreview) {
        Image(
            painter = painterResource(id = defaultImageResId),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
        return
    }

    val painter = rememberGlidePainter(
        request = request,
        previewPlaceholder = previewPlaceholderResId
    )

    val glideImagePainter = if (painter.loadState is ImageLoadState.Success) {
        painter
    } else {
        if (defaultImageResId == 0) return
        painterResource(id = defaultImageResId)
    }

    Image(
        painter = glideImagePainter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
