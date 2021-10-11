package com.example.composeexample02.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun GlideImage(
    modifier: Modifier = Modifier,
    request: Any,
    contentScale: ContentScale = ContentScale.Fit,
) {

    val painter = rememberGlidePainter(
        request = request
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
