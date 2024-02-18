package com.poetofcode.site2api_sample.data.service

import com.poetofcode.site2api_sample.data.model.DataResponse
import com.poetofcode.site2api_sample.data.model.FeedResponse
import retrofit2.Call
import retrofit2.http.*

interface FreshApiService {

    @GET("feed")
    fun fetchFeed(): Call<DataResponse<FeedResponse>>

}