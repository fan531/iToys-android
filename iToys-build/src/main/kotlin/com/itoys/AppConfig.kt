package com.itoys

import com.android.builder.model.ApiVersion

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/10/2022
 * @desc app相关 config.
 */
object AppConfig {

    const val compileSdkVersion: Int = 33

    // 最高支持到android 13.
    val targetSdkVersion: ApiVersion = object : ApiVersion {
        override fun getApiLevel(): Int = 33

        override fun getCodename(): String = "Tiramisu"

        override fun getApiString(): String = "Android 13"
    }

    // 最低版本支持到android 6.
    val minSdkVersion: ApiVersion = object : ApiVersion {
        override fun getApiLevel(): Int = 23

        override fun getCodename(): String = "Marshmallow"

        override fun getApiString(): String = "Android 6"
    }
    const val buildToolsVersion: String = "33.0.1"

    // app 包名, 根据项目做修改.
    const val appId: String = "com.itoys.app"

    // app 名称.
    const val appName: String = "iToys"

    // app版本code.
    const val versionCode: Int = 1

    private const val majorVersion: Int = 1
    private const val minorVersion: Int = 0
    const val patchVersion: Int = 0

    // app版本名称.
    const val versionName: String = "$majorVersion.$minorVersion.$patchVersion"

    // 依赖.
    const val testRunner: String = "androidx.test.runner.AndroidJUnitRunner"
    const val defaultProguardFile: String = "proguard-android-optimize.txt"
    const val proguardRulesFile: String = "proguard-rules.pro"

    // build 变体
    const val flavorDimensions: String = "iToys"
}