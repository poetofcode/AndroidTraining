package com.poetofcode.site2api_sample

import androidx.lifecycle.ViewModel
import com.poetofcode.site2api_sample.data.model.FeedResponse
import com.poetofcode.site2api_sample.data.repository.FeedRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val feedRepository = FeedRepository()

    init {
        loadFeed()
    }

    private fun loadFeed() {
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