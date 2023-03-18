package com.itoys.depends

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/10/2022
 * @desc Android Depends.
 */
object Depends {

    /** Kotlin相关. */

    // Kotlin 基础.
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    // Kotlin 携程.
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.1"
    const val ksp = "com.google.devtools.ksp:symbol-processing-api:${Versions.ksp}"
    const val kotlinpoet = "com.squareup:kotlinpoet-ksp:1.12.0"

    /** 极低成本的 Android 屏幕适配方案 */
    const val autosize = "com.github.JessYanCoding:AndroidAutoSize:v1.2.1"

    /** json 解析 */
    const val gson = "com.google.code.gson:gson:2.10.1"
    const val jackson = "com.fasterxml.jackson.module:jackson-module-kotlin:2.14.+"

    /** 沉浸式状态栏和沉浸式导航栏管理 */
    const val immersionbar = "com.geyifeng.immersionbar:immersionbar:${Versions.immersionbar}"
    const val immersionbar_ktx = "com.geyifeng.immersionbar:immersionbar-ktx:${Versions.immersionbar}"

    /** 标题栏 */
    const val titlebar = "com.github.getActivity:TitleBar:${Versions.titlebar}"

    /** 智能下拉刷新框架 */
    const val refresh = "io.github.scwang90:refresh-layout-kernel:${Versions.refresh}"
    const val refresh_header_classics = "io.github.scwang90:refresh-header-classics:${Versions.refresh}"
    const val refresh_header_falsify = "io.github.scwang90:refresh-header-falsify:${Versions.refresh}"
    const val refresh_header_two_level = "io.github.scwang90:refresh-header-two-level:${Versions.refresh}"
    const val refresh_footer_classics = "io.github.scwang90:refresh-footer-classics:${Versions.refresh}"

    /** 网络相关 */
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

    /** okio I/O library for Android */
    const val okio = "com.squareup.okio:okio:${Versions.okio}"

    /** 路由相关. */

    /** 货拉拉路由. */
    const val router = "cn.therouter:router:1.1.1"
    const val router_apt = "cn.therouter:apt:1.1.1"

    /** 阿里巴巴路由 */

    /**
     * 依赖版本号.
     */
    private object Versions {
        /** Kotlin version */
        const val kotlin = "1.6.21"

        /** ksp version */
        const val ksp = "1.8.10-1.0.9"

        /** 沉浸式状态栏 version */
        const val immersionbar = "3.2.2"

        /** 智能下拉刷新框架 version */
        const val refresh = "2.0.5"

        /** 标题栏 version */
        const val titlebar = "10.0"

        /** 网络相关 */
        const val retrofit = "2.9.0"
        const val okhttp = "4.10.0"

        /** okio */
        const val okio = "3.3.0"

        /** 路由相关. */
        // 货拉拉路由.
        const val router_therouter = "1.1.1"
        // 阿里路由.
        const val router_ali = "1.111"
    }
}