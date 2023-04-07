package com.itoys.kit

import android.app.Application
import android.graphics.drawable.Drawable
import com.itoys.expansion.invalid
import com.itoys.kit.core.IToysKitManager
import com.itoys.kit.core.IToysKitViewManager
import com.itoys.kit.repository.IToysKitRepository
import com.itoys.logcat.LogPriority
import com.itoys.logcat.LogcatInitialization
import com.itoys.network.NetworkInitialization
import com.itoys.utils.ActivityUtils
import com.itoys.utils.UtilsInitialization

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 25/03/2023
 * @desc real itoys kit
 */
internal object IToysKitReal {

    private lateinit var APPLICATION: Application

    fun install(application: Application) {
        APPLICATION = application

        // 初始化 logcat
        LogcatInitialization.initialization(application, LogPriority.VERBOSE)
        // 初始化工具类
        UtilsInitialization.initialization(application)
        // 初始化 http
        initializationNetwork()
        // 注册 activity 生命周期回调
        application.registerActivityLifecycleCallbacks(IToysKitActivityLifecycleCallbacksImpl.INSTANCE)
    }

    fun setAlwaysShowEnterIcon(alwaysShow: Boolean) {
        IToysKitManager.ALWAYS_SHOW_ENTER_ICON = alwaysShow
    }

    fun setCustomEnterIcon(iToysIconDrawable: Drawable?) {
        IToysKitManager.ENTER_ICON_DRAWABLE = iToysIconDrawable
    }

    fun requireApp(): Application {
        return APPLICATION
    }

    /**
     * 初始化网络
     */
    private fun initializationNetwork() {
        // 初始化网络 base url
        NetworkInitialization.initialization(IToysKitRepository.kitNetworkUrl.invalid())
    }

    /**
     * 直接显示工具面板页面
     */
    fun showToolPanel() {
        IToysKitViewManager.INSTANCE.attachToolPanel(ActivityUtils.getTopActivity())
    }
}