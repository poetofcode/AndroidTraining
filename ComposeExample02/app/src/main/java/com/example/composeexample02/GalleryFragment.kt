package com.example.composeexample02

import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.composeexample02.composable.GlideImage
import com.example.composeexample02.entity.FakeData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

class GalleryFragment : Fragment() {

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
        val pagerState = rememberPagerState()

        val current = pagerState.currentPage
        val target = pagerState.targetPage ?: current

        Box(
            Modifier
                .background(Color.Black)
                .fillMaxSize()) {
            HorizontalPager(
                count = images.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                itemSpacing = 0.dp
            ) { pageIdx ->

                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    request = images[pageIdx],
                    contentScale = ContentScale.Fit
                )

            }
        }
    }


}