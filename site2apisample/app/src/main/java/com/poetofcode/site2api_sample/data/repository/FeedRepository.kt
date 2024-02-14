package com.poetofcode.site2api_sample.data.repository

import com.poetofcode.site2api_sample.data.model.FeedResponse
import com.poetofcode.site2api_sample.data.service.FreshApiService
import retrofit2.Call

class FeedRepository(val api: FreshApiService) {

    fun fetchFeed() : Call<FeedResponse> {
        return api.fetchFeed()
    }

}