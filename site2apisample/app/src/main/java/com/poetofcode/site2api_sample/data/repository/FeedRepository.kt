package com.poetofcode.site2api_sample.data.repository

import com.poetofcode.site2api_sample.data.service.FreshApiService
import com.poetofcode.site2api_sample.data.service.buildNetworkCallback

class FeedRepository(val api: FreshApiService) {

    fun fetchFeed() = buildNetworkCallback(api.fetchFeed())

}