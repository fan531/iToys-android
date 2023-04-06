package com.itoys.network

import com.itoys.logcat.logcat
import com.itoys.network.http.initRetrofit

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/03/2023
 * @desc 网络初始化
 */
object NetworkInitialization {

    fun initialization(apiUrl: String = BuildConfig.PROD_API_URL) {
        if (apiUrl.isBlank()) return
        logcat { "当前网络Host: $apiUrl" }
        initRetrofit(apiUrl)
    }
}