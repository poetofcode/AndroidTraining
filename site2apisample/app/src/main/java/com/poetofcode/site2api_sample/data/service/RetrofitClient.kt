package com.poetofcode.site2api_sample.data.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofit: Retrofit? = null

    private var token = ""

    fun calculateToken(path: String, salt: String) : String {
        return (path.toSha1() + token.toSha1() + salt.toSha1()).toSha1()
    }

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(
                Interceptor { chain ->
                    val request = chain.request()
                    if (request.url.toString().contains("site/token")) {
                        return@Interceptor chain.proceed(request)
                    }

                    val salt = "secret-api-key"
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${calculateToken(request.url.encodedPath, salt)}")
                        .build()
                    var response = chain.proceed(newRequest)

                    if (response.code == 401) {
                        response.close()

                        // TOKEN REFRESH
                        val newTokenResponse = ServiceProvider.freshApiService.fetchToken().execute()
                        val newToken = newTokenResponse.body()?.result?.token
                        if (newToken != null) {
                            token = newToken
                        }

                        chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer ${calculateToken(request.url.encodedPath, salt)}")
                            .build()
                            .let { newReq ->
                                response = chain.proceed(newReq)
                            }
                    }
                    response
                }
            ).addInterceptor(interceptor).build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!
    }
}