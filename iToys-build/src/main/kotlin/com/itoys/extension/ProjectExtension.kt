package com.itoys.extension

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.itoys.AppConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import java.io.File

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 04/03/2023
 * @desc
 */
/** app xml配置项. */
private val appManifestPlaceholders: HashMap<String, Any> by lazy {
    hashMapOf<String, Any>().apply {
        // app 名称.
        put("app_name", AppConfig.appName)
    }
}

/** app dev环境 资源配置. */
private val debugAppManifestPlaceholders: HashMap<String, Any> by lazy {
    hashMapOf<String, Any>().apply {
        // app 名称.
        put("app_name", "${AppConfig.appName}-debug")
    }
}

/** app preview环境 资源配置. */
private val previewAppManifestPlaceholders: HashMap<String, Any> by lazy {
    hashMapOf<String, Any>().apply {
        // app 名称.
        put("app_name", "${AppConfig.appName}-pre")
    }
}

/** app uat环境 资源配置. */
private val uatAppManifestPlaceholders: HashMap<String, Any> by lazy {
    hashMapOf<String, Any>().apply {
        // app 名称.
        put("app_name", AppConfig.appName)
    }
}

/**
 * application 模块配置.
 */
fun BaseAppModuleExtension.appConfig(
    project: Project,
    useViewBiding: Boolean = true,
) {
    namespace = AppConfig.appId
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = AppConfig.appId
        minSdk = AppConfig.minSdkVersion.apiLevel
        targetSdk = AppConfig.targetSdkVersion.apiLevel
        testInstrumentationRunner = AppConfig.testRunner
        multiDexEnabled = true
        versionName = AppConfig.versionName
        versionCode = AppConfig.versionCode
    }

    signingConfigs {
        getByName("debug") {
            storeFile = File("${project.rootDir}/itoys-build/keystore/itoys.keystore")
            storePassword = "itoys2023"
            keyAlias = "itoys"
            keyPassword = "itoys2023"
        }

        create("release") {
            storeFile = File("${project.rootDir}/itoys-build/keystore/itoys.keystore")
            storePassword = "itoys2023"
            keyAlias = "itoys"
            keyPassword = "itoys2023"
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile(AppConfig.defaultProguardFile), AppConfig.proguardRulesFile)

            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile(AppConfig.defaultProguardFile), AppConfig.proguardRulesFile)

            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions += AppConfig.flavorDimensions

    productFlavors {
        // 开发
        create("Ver-Dev") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            testNamespace = "${AppConfig.appId}$applicationIdSuffix"

            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders.putAll(debugAppManifestPlaceholders)
        }

        // 测试
        create("Ver-Test") {
            applicationIdSuffix = ".test"
            versionNameSuffix = "-test"
            testNamespace = "${AppConfig.appId}$applicationIdSuffix"

            manifestPlaceholders.putAll(previewAppManifestPlaceholders)
        }

        // 预发布
        create("Ver-Uat") {
            applicationIdSuffix = ".uat"
            versionNameSuffix = "-uat"
            testNamespace = "${AppConfig.appId}$applicationIdSuffix"
            manifestPlaceholders.putAll(uatAppManifestPlaceholders)
        }

        // 线上生产
        create("Ver-Prod") {
            manifestPlaceholders.putAll(appManifestPlaceholders)
        }
    }

    buildFeatures {
        viewBinding = useViewBiding
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    lint {
        // set to true to turn off analysis progress reporting by lint
        quiet = true
        // if true, stop the gradle build if errors are found
        abortOnError = false
        // set to true to have all release builds run lint on issues with severity=fatal
        // and abort the build (controlled by abortOnError above) if fatal issues are found
        checkReleaseBuilds = true
        // if true, only report errors
        ignoreWarnings = true
        // if true, emit full/absolute paths to files with errors (true by default)
        //absolutePaths = true
        // if true, check all issues, including those that are off by default
        checkAllWarnings = true
        // if true, treat all warnings as errors
        warningsAsErrors = true
        // turn off checking the given issue id's
        disable.plus("RemoveWorkManagerInitializer")
    }
}