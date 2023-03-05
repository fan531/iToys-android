package com.itoys.extension

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.itoys.AppConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
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
        put("app_name", AppConfig.appName)
    }
}

/** app preview环境 资源配置. */
private val previewAppManifestPlaceholders: HashMap<String, Any> by lazy {
    hashMapOf<String, Any>().apply {
        // app 名称.
        put("app_name", AppConfig.appName)
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
    useAliRouter: Boolean = false,
    useViewBiding: Boolean = true,
) {
    project.router(useAliRouter = useAliRouter)

    namespace = AppConfig.appId
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = AppConfig.appId
        minSdk = AppConfig.minSdkVersion.apiLevel
        targetSdk = AppConfig.targetSdkVersion.apiLevel
        testInstrumentationRunner = AppConfig.testRunner
        multiDexEnabled = true
    }

    buildTypes {
        // dev
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile(AppConfig.defaultProguardFile), AppConfig.proguardRulesFile)

            defaultConfig.applicationId = "${AppConfig.appId}.debug"
            defaultConfig.versionCode = AppConfig.debugVersionCode
            defaultConfig.versionName = AppConfig.debugVersionName

            manifestPlaceholders.putAll(debugAppManifestPlaceholders)
        }

        // test
        create("preview") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile(AppConfig.defaultProguardFile), AppConfig.proguardRulesFile)

            defaultConfig.applicationId = "${AppConfig.appId}.preview"
            defaultConfig.versionCode = AppConfig.previewVersionCode
            defaultConfig.versionName = AppConfig.previewVersionName

            manifestPlaceholders.putAll(previewAppManifestPlaceholders)
        }

        // uat
        create("uat") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile(AppConfig.defaultProguardFile), AppConfig.proguardRulesFile)

            defaultConfig.applicationId = "${AppConfig.appId}.uat"
            defaultConfig.versionName = AppConfig.uatVersionName
            manifestPlaceholders.putAll(uatAppManifestPlaceholders)
        }

        // release
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile(AppConfig.defaultProguardFile), AppConfig.proguardRulesFile)

            defaultConfig.applicationId = AppConfig.appId
            defaultConfig.versionCode = AppConfig.versionCode
            defaultConfig.versionName = AppConfig.versionName
            manifestPlaceholders.putAll(appManifestPlaceholders)
        }
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

/**
 * library 模块配置.
 */
fun LibraryExtension.libraryConfig(
    project: Project,
    useAliRouter: Boolean = false,
) {
    project.router(useAliRouter = useAliRouter)

    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion.apiLevel
        targetSdk = AppConfig.targetSdkVersion.apiLevel
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

/**
 * 路由配置.
 */
fun Project.router(
    useAliRouter: Boolean = false,
    useTheRouter: Boolean = false,
) {
    if (useAliRouter) {
        (extensions.getByName("kapt") as KaptExtension).apply {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }
    }
}