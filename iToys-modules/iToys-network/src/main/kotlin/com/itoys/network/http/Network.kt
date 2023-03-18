package com.itoys.network.http

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 18/03/2023
 * @desc
 */

private const val TIME_OUT: Long = 30L

private var retrofit: Retrofit? = null

private val okClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .callTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .followRedirects(false)
        .build()
}

fun initRetrofit(apiUrl: String) {
    retrofit = Retrofit.Builder()
        .baseUrl(apiUrl.toHttpUrl())
        .client(okClient)
        .build()
}

fun <T> Class<T>.toApiService(): T {
    if (retrofit == null) throw UninitializedPropertyAccessException("Retrofit必须初始化")

    return retrofit!!.create(this)
}