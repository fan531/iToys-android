package com.itoys.kit

import android.app.Application
import com.itoys.logcat.LogPriority
import com.itoys.logcat.LogcatInitialization
import com.itoys.utils.UtilsInitialization

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc real itoys kit
 */
internal object IToysKitReal {

    private lateinit var APPLICATION: Application

    fun install(application: Application) {
        APPLICATION = application

        // 初始化 logcat
        LogcatInitialization.initialization(application, LogPriority.DEBUG)
        // 初始化工具类
        UtilsInitialization.initialization(application)
    }

    fun requireApp(): Application {
        return APPLICATION
    }
}