package com.poetofcode.site2api_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.poetofcode.site2api_sample.data.model.FeedResponse
import com.poetofcode.site2api_sample.ui.theme.Site2apisampleTheme
import java.util.Properties

class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Site2apisampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenContent()
                }
            }
        }

        onScreenSetup()
    }

    private fun onScreenSetup() {
        val baseUrl = resources.getString(R.string.backendBaseUrl)
        val apiKey = resources.getString(R.string.apiKey)
        vm.initAPI(baseUrl, apiKey)
        vm.loadFeed()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ScreenContent() {
        val state = rememberPullRefreshState(vm.screenState.value.isLoading, vm::loadFeed)

        Scaffold(

        ) {
            Box(modifier = Modifier.fillMaxSize().pullRefresh(state)) {
                LazyColumn(
                    modifier = Modifier.padding(it),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(vm.screenState.value.posts) { post ->
                        Post(post = post)
                    }
                }
                PullRefreshIndicator(
                    vm.screenState.value.isLoading,
                    state,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }

    @Composable
    private fun Post(post: FeedResponse.Post) {
        Column(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFEAEAEA))
                .padding(8.dp)
        ) {
            Text(text = post.title.orEmpty(), fontSize = 16.sp)
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Site2apisampleTheme {
            Greeting("Android")
        }
    }
}
