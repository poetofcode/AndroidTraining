package com.poetofcode.site2api_sample

import androidx.lifecycle.ViewModel
import com.poetofcode.site2api_sample.data.model.FeedResponse
import com.poetofcode.site2api_sample.data.repository.FeedRepository
import com.poetofcode.site2api_sample.data.service.ServiceProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private lateinit var feedRepository: FeedRepository

    fun initAPI(baseUrl: String) {
        // TODO move to App class
        ServiceProvider.buildService(baseUrl)
        feedRepository = FeedRepository(ServiceProvider.freshApiService)
    }

    fun loadFeed() {
        feedRepository.fetchFeed().enqueue(object : Callback<FeedResponse> {
            override fun onResponse(call: Call<FeedResponse>, response: Response<FeedResponse>) {
                println("mylog Is success: ${response.isSuccessful}")
            }

            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                println("mylog Failure: ${t}")
            }
        })
    }

}