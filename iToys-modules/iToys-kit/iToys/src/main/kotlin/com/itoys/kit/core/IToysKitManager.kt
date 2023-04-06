package com.itoys.kit.core

import android.graphics.drawable.Drawable
import com.itoys.kit.IToysKitCallback

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 26/03/2023
 * @desc
 */
object IToysKitManager {

    /**
     * 是否显示入口icon
     */
    @JvmField
    var ALWAYS_SHOW_ENTER_ICON = true

    /**
     * 入口icon
     */
    @JvmField
    var ENTER_ICON_DRAWABLE: Drawable? = null

    /**
     * icon主入口是否处于显示状态
     */
    @JvmField
    var ENTER_ICON_HAS_SHOW = false

    /**
     * 全局回调
     */
    var CALLBACK: IToysKitCallback? = null

    /**
     * Wifi IP 地址
     */
    val IP_ADDRESS_BY_WIFI: String = ""

    /**
     * activity lifecycle info map.
     */
    val ACTIVITY_LIFECYCLE_INFO: MutableMap<String, ActivityLifecycleStatusInfo?> by lazy { mutableMapOf() }
}