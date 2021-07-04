package com.example.composeexample02

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

class OnboardFragment : Fragment() {

    @ExperimentalPagerApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Splash()
            }
        }
    }

    @ExperimentalPagerApi
    @Preview
    @Composable
    fun Splash() {
        Surface(
            color = Color.Blue
        ) {
            val pagerState = rememberPagerState(pageCount = 3)

            HorizontalPager(state = pagerState) { page ->
                // Our page content
                Text(
                    text = "Page: $page",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}