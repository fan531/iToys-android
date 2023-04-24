package com.itoys.extension

import com.android.build.gradle.LibraryExtension
import com.itoys.AppConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 24/04/2023
 * @desc library 模块配置.
 */

fun LibraryExtension.libraryConfig(
    project: Project,
    namespaceId: String,
    useViewBiding: Boolean = true,
) {
    compileSdk = AppConfig.compileSdkVersion

    namespace = namespaceId

    defaultConfig {
        minSdk = AppConfig.minSdkVersion.apiLevel
        targetSdk = AppConfig.targetSdkVersion.apiLevel
    }

    buildFeatures {
        viewBinding = useViewBiding
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}