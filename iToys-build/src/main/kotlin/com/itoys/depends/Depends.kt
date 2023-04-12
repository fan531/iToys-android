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

    /** Android上最好的RecyclerView框架, 比 BRVAH 更简单强大 */
    const val brv = "com.github.liangjingkanji:BRV:${Versions.brv}"

    /** Type-safe Multiplatform cryptography library for Kotlin */
    const val cryptography = "dev.whyoleg.cryptography:cryptography-core:0.1.0"

    /** json 解析 */
    const val gson = "com.google.code.gson:gson:2.10.1"
    const val jackson = "com.fasterxml.jackson.module:jackson-module-kotlin:2.14.+"

    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.10"

    /** 沉浸式状态栏和沉浸式导航栏管理 */
    const val immersionbar = "com.geyifeng.immersionbar:immersionbar:${Versions.immersionbar}"
    const val immersionbar_ktx = "com.geyifeng.immersionbar:immersionbar-ktx:${Versions.immersionbar}"

    /** MMKV——基于 mmap 的高性能通用 key-value 组件 */
    const val mmkv = "com.tencent:mmkv:${Versions.mmkv}"

    /** 标题栏 */
    const val titlebar = "com.github.getActivity:TitleBar:${Versions.titlebar}"

    /** loading */
    const val spin = "com.github.ybq:Android-SpinKit:1.4.0"

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
        /** BRV */
        const val brv = "1.3.90"

        /** Kotlin */
        const val kotlin = "1.6.21"

        /** ksp */
        const val ksp = "1.8.10-1.0.9"

        /** 沉浸式状态栏 */
        const val immersionbar = "3.2.2"

        /** mmkv */
        const val mmkv = "1.2.15"

        /** 智能下拉刷新框架 */
        const val refresh = "2.0.5"

        /** 标题栏 */
        const val titlebar = "10.0"

        /** 网络相关 */
        const val retrofit = "2.9.0"
        const val okhttp = "4.10.0"

        /** okio */
        const val okio = "3.3.0"

        /** 路由相关. */
        /** 货拉拉路由 */
        const val router_therouter = "1.1.1"
        /** 阿里路由 */
        const val router_ali = "1.111"
    }
}