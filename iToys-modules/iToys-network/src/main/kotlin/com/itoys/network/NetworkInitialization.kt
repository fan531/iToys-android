package com.itoys.network

import com.itoys.network.http.initRetrofit

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/03/2023
 * @desc
 */
object NetworkInitialization {

    fun initialization(apiUrl: String = BuildConfig.PROD_API_URL) {
        initRetrofit(apiUrl)
    }
}