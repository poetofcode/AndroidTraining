package com.poetofcode.site2api_sample

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

class MainViewModel : ViewModel() {

    private lateinit var feedRepository: FeedRepository

    fun initAPI(baseUrl: String, apiKey: String) {
        // TODO move to App class
        ServiceProvider.buildService(baseUrl, apiKey)
        feedRepository = FeedRepository(ServiceProvider.freshApiService)
    }

    fun loadFeed() {
        feedRepository.fetchFeed().onResult { result ->
            when (result) {
                is ExceptionResponse -> {
                    println("mylog Exception ${result}")
                }
                is FailureResponse -> {
                    println("mylog Failure ${result}")
                }
                is DataResponse -> {
                    println("mylog Success ${result.result}")
                }
            }
        }
    }

}