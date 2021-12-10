package com.example.composeexample02

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.compose.rememberImagePainter
import com.example.composeexample02.model.Cat
import com.example.composeexample02.model.FragViewModel

class CommandFragment : Fragment() {
    private val model: FragViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieList(model.cats)
            }
        }
    }
}

@Composable
fun MovieList(movies: List<Cat>) {
    Column {
        Text(
            text = "КотоФильмы",
            fontSize = 24.sp,
            color = Color.Blue,
            modifier = Modifier.padding(10.dp)
        )

        LazyColumn {
            items(items = movies) {
                MovieItem(movie = it)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Cat) {
    Column(Modifier.padding(10.dp).background(Color.LightGray).fillMaxWidth()) {
        Text(
            text = movie.text,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )

        //
        // Cat Avatar
        //
        val painter = rememberImagePainter(
            data = movie.imageUrl,
            builder = {
                crossfade(true)
                // placeholder(previewPlaceholderResId)
            }
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp),
            contentScale = ContentScale.Crop
        )

        //
        // Like button
        //
        Text(
            text = "Нравится",
            fontSize = 16.sp,
            color = if (movie.isFavorite) Color.Red else Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp).align(Alignment.End)
        )
    }
}
