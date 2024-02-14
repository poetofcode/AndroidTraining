package com.poetofcode.site2api_sample.data.service

object ServiceProvider {

    lateinit var freshApiService: FreshApiService

    fun buildService(baseUrl: String) {
        freshApiService = RetrofitClient.getClient(baseUrl).create(FreshApiService::class.java)
    }

}