package com.poetofcode.site2api_sample.data.service

import com.poetofcode.site2api_sample.data.model.DataResponse
import com.poetofcode.site2api_sample.data.model.ExceptionResponse
import com.poetofcode.site2api_sample.data.model.FailureResponse
import com.poetofcode.site2api_sample.data.model.NetworkCallback
import com.poetofcode.site2api_sample.data.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> buildNetworkCallback(apiCall: Call<DataResponse<T>>) : NetworkCallback<T> {
    val networkCallback = object : NetworkCallback<T> {
        private var pendingCallback : ((result: ResultResponse<T>) -> Unit)? = null

        fun postResult(result: ResultResponse<T>? = null) {
            result?.let {
                pendingCallback?.invoke(it)
            }
        }

        override fun onResult(cb: (result: ResultResponse<T>) -> Unit) {
            pendingCallback = cb
        }
    }

    apiCall.enqueue(object : Callback<DataResponse<T>> {
        override fun onResponse(call: Call<DataResponse<T>>, response: Response<DataResponse<T>>) {
            val resp = response.body()
            if (response.isSuccessful && resp != null) {
                networkCallback.postResult(resp)

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

        override fun onFailure(call: Call<DataResponse<T>>, t: Throwable) {
            networkCallback.postResult(ExceptionResponse(error = t))
        }

    })

    return networkCallback
}
