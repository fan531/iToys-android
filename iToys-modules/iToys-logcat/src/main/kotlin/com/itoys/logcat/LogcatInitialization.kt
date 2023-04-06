package com.itoys.logcat

import android.app.Application

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc 日志初始化
 */
object LogcatInitialization {

    private lateinit var APPLICATION: Application

    fun initialization(application: Application, minPriority: LogPriority = LogPriority.DEBUG) {
        APPLICATION = application
        // 初始化日志目录

        // 日志初始化
        AndroidLogcatLogger.installOnDebuggableApp(APPLICATION, minPriority)
    }

    fun requireApp(): Application {
        return APPLICATION
    }
}