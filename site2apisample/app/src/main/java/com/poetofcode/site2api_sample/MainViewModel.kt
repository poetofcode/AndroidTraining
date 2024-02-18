package com.poetofcode.site2api_sample

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poetofcode.site2api_sample.data.model.DataResponse
import com.poetofcode.site2api_sample.data.model.ExceptionResponse
import com.poetofcode.site2api_sample.data.model.FailureResponse
import com.poetofcode.site2api_sample.data.model.FeedResponse
import com.poetofcode.site2api_sample.data.model.ResultResponse
import com.poetofcode.site2api_sample.data.repository.FeedRepository
import com.poetofcode.site2api_sample.data.service.ServiceProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class FeedScreenState(
    val posts: List<FeedResponse.Post>,
    val isError: Boolean,
    val isLoading: Boolean
) {
    companion object {
        val INITIAL = FeedScreenState(
            posts = emptyList(),
            isError = false,
            isLoading = false
        )
    }
}

class MainViewModel : ViewModel() {

    private lateinit var feedRepository: FeedRepository

    private val _screenState = mutableStateOf<FeedScreenState>(FeedScreenState.INITIAL)
    val screenState : State<FeedScreenState> = _screenState

    fun initAPI(baseUrl: String, apiKey: String) {
        // TODO move to App class
        ServiceProvider.buildService(baseUrl, apiKey)
        feedRepository = FeedRepository(ServiceProvider.freshApiService)
    }

    fun loadFeed() {
        _screenState.value = _screenState.value.copy(
            isLoading = true
        )
        feedRepository.fetchFeed().onResult { result ->
            _screenState.value = _screenState.value.copy(
                isLoading = false
            )

            when (result) {
                is ExceptionResponse -> {
                    _screenState.value = _screenState.value.copy(
                        isError = true
                    )
                }
                is FailureResponse -> {
                    _screenState.value = _screenState.value.copy(
                        isError = true
                    )
                }
                is DataResponse -> {
                    _screenState.value = _screenState.value.copy(
                        posts = result.result.posts.orEmpty()
                    )
                }
            }
        }
    }

}