package com.poetofcode.site2api_sample.data.model

sealed class ResultResponse<T>(
    val isError: Boolean
)

data class DataResponse<T>(
    val result: T,
) : ResultResponse<T>(false)

data class FailureResponse<T>(
    val status: Int,
    val code: String,
    val description: String,
) : ResultResponse<T>(true)

data class ExceptionResponse<T>(
    val error: Throwable,
) : ResultResponse<T>(true)


fun interface NetworkCallback<T> {
    fun onResult(cb : (result: ResultResponse<T>) -> Unit)
}