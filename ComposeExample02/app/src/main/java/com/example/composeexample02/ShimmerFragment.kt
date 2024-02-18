package com.example.composeexample02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.composeexample02.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi


class ShimmerFragment: Fragment() {

    val ShimmerColorShades = listOf(

        Color.LightGray.copy(0.9f),

        Color.LightGray.copy(0.2f),

        Color.LightGray.copy(0.9f)

    )

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

    @Preview
    @Composable
    fun Root() {
        ShimmerAnimationTheme(darkTheme = false) {
            Surface(color = MaterialTheme.colors.background) {

                /*
                  Lazy column as I am adding multiple items for display purpose
                  create you UI according to requirement
                 */
                LazyColumn {

                    /**
                    Lay down the Shimmer Animated item 5 time
                    [repeat] is like a loop which executes the body
                    according to the number specified
                     */
                    repeat(5) {
                        item {
                            ShimmerAnimation()

                        }
                    }
                }
            }
        }
    }


    @Composable
    fun ShimmerAnimation(
    ) {

        /*
        Create InfiniteTransition
        which holds child animation like [Transition]
        animations start running as soon as they enter
        the composition and do not stop unless they are removed
        */
        val transition = rememberInfiniteTransition()
        val translateAnim by transition.animateFloat(
            /*
            Specify animation positions,
            initial Values 0F means it starts from 0 position
            */
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(

                /*
                 Tween Animates between values over specified [durationMillis]
                */
                tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                RepeatMode.Reverse
            )
        )

        /*
          Create a gradient using the list of colors
          Use Linear Gradient for animating in any direction according to requirement
          start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
          end= Animate the end position to give the shimmer effect using the transition created above
        */
        val brush = Brush.linearGradient(
            colors = ShimmerColorShades,
            start = Offset(10f, 10f),
            end = Offset(translateAnim, translateAnim)
        )

        ShimmerItem(brush = brush)

    }


    @Composable
    fun ShimmerItem(
        brush: Brush
    ) {

        /*
          Column composable shaped like a rectangle,
          set the [background]'s [brush] with the
          brush receiving from [ShimmerAnimation]
          which will get animated.
          Add few more Composable to test
        */
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(vertical = 8.dp)
                    .background(brush = brush)
            )
        }
    }

    private val DarkColorPalette = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200
    )

    private val LightColorPalette = lightColors(
        primary = Purple500,
        primaryVariant = Purple700,
        secondary = Teal200

        /* Other default colors to override
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = Color.Black,
        onSurface = Color.Black,
        */
    )

    @Composable
    fun ShimmerAnimationTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable() () -> Unit
    ) {
        val colors = if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }

        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}