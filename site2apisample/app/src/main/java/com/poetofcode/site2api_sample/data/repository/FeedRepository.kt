package com.poetofcode.site2api_sample.data.repository

import com.poetofcode.site2api_sample.data.model.DataResponse
import com.poetofcode.site2api_sample.data.model.ExceptionResponse
import com.poetofcode.site2api_sample.data.model.FailureResponse
import com.poetofcode.site2api_sample.data.model.FeedResponse
import com.poetofcode.site2api_sample.data.model.NetworkCallback
import com.poetofcode.site2api_sample.data.model.ResultResponse
import com.poetofcode.site2api_sample.data.service.FreshApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedRepository(val api: FreshApiService) {

    fun fetchFeed(): NetworkCallback<FeedResponse> {

        val networkCallback = object : NetworkCallback<FeedResponse> {
            private var pendingCallback : ((result: ResultResponse<FeedResponse>) -> Unit)? = null

            fun postResult(result: ResultResponse<FeedResponse>? = null) {
                result?.let {
                    pendingCallback?.invoke(it)
                }
            }

            override fun onResult(cb: (result: ResultResponse<FeedResponse>) -> Unit) {
                pendingCallback = cb
            }
        }

        api.fetchFeed().enqueue(object : Callback<DataResponse<FeedResponse>> {
            override fun onResponse(call: Call<DataResponse<FeedResponse>>, response: Response<DataResponse<FeedResponse>>) {
                val feedResponse = response.body()
                if (response.isSuccessful && feedResponse != null) {
                    networkCallback.postResult(feedResponse)

                } else {
                    //
                    // TODO parse failure json
                    //
                    networkCallback.postResult(FailureResponse(
                        status = 500,
                        code = "-1",
                        description = "Who knows"
                    ))
                }
            }

            override fun onFailure(call: Call<DataResponse<FeedResponse>>, t: Throwable) {
                networkCallback.postResult(ExceptionResponse(error = t))
            }

        })

        return networkCallback
    }

}