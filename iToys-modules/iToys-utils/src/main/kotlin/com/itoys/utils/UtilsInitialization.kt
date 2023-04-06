package com.itoys.utils

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc 工具类初始化
 */
object UtilsInitialization {

    private lateinit var APPLICATION: Application

    fun initialization(application: Application) {
        APPLICATION = application

        UtilsBridge.init(APPLICATION)
        MMKV.initialize(APPLICATION)
    }

    fun requireApp(): Application {
        return APPLICATION
    }
}