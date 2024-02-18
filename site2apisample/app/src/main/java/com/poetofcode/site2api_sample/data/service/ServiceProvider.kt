package com.poetofcode.site2api_sample.data.service

object ServiceProvider {

    lateinit var freshApiService: FreshApiService

    fun buildService(baseUrl: String, apiKey: String) {
        freshApiService = RetrofitClient.getClient(baseUrl, apiKey).create(FreshApiService::class.java)
    }

}