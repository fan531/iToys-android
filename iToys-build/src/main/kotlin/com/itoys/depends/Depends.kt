package com.itoys.depends

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/10/2022
 * @desc Android Depends.
 */
object Depends {

    /** Kotlin相关. */

    // Kotlin 基础.
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    // Kotlin 携程.
    const val kotlinx_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"

    /** 路由相关. */

    /** 货拉拉路由. */
    const val router = "cn.therouter:router:1.1.1"
    const val router_apt = "cn.therouter:apt:1.1.1"

    /** 阿里巴巴路由 */

    /**
     * 依赖版本号.
     */
    private object Versions {
        /** Kotlin version. */
        const val kotlin_version = "1.6.21"

        /** 路由相关. */
        // 货拉拉路由.
        const val router_therouter = "1.1.1"
        // 阿里路由.
        const val router_ali = "1.111"
    }
}