package com.poetofcode.site2api_sample.data.service

import com.poetofcode.site2api_sample.data.model.DataResponse
import com.poetofcode.site2api_sample.data.model.FeedResponse
import com.poetofcode.site2api_sample.data.model.TokenResponse
import retrofit2.Call
import retrofit2.http.*

interface FreshApiService {

    @GET("fresh/feed")
    fun fetchFeed(): Call<DataResponse<FeedResponse>>

    @POST("token")
    fun fetchToken() : Call<DataResponse<TokenResponse>>

}