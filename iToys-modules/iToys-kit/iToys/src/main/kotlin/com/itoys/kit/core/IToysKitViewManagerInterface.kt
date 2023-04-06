package com.itoys.kit.core

import android.app.Activity

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 26/03/2023
 * @desc 页面浮标管理类接口
 */
interface IToysKitViewManagerInterface {

    /**
     * 在当前Activity中添加指定悬浮窗
     */
    fun attach(itoysKitIntent: IToysKitIntent)

    /**
     * 移除每个activity指定的itoysKitView
     */
    fun detach(itoysKitView: AbsIToysKitView)

    /**
     * 移除每个activity指定的itoysKitView tag
     */
    fun detach(tag: String)

    /**
     * 移除指定的itoysKitView
     */
    fun detach(itoysKitViewClass: Class<out  AbsIToysKitView>)

    /**
     * 移除所有activity的所有itoysKitView
     */
    fun detachAll()

    /**
     * 获取页面上指定的 Kit View
     */
    fun <T : AbsIToysKitView> getIToysKitView(activity: Activity?, clazz: Class<T>): AbsIToysKitView?

    /**
     * 获取页面上所有的 Kit View
     */
    fun getIToysKitViews(activity: Activity?): Map<String, AbsIToysKitView>

    fun notifyForeground()

    fun notifyBackground()

    fun dispatchOnActivityResumed(activity: Activity?)

    fun onActivityPaused(activity: Activity?)

    fun onActivityStopped(activity: Activity?)

    fun onActivityDestroyed(activity: Activity?)
}
