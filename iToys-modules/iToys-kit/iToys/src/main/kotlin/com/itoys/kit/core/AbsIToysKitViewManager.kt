package com.itoys.kit.core

import android.app.Activity

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc 页面浮标管理类接口
 */
abstract class AbsIToysKitViewManager : IToysKitViewManagerInterface {

    /**
     * 添加主icon
     */
    abstract fun attachMainIcon(activity: Activity?)

    /**
     * 移除主icon
     */
    abstract fun detachMainIcon()

    /**
     * 添加toolPanel
     */
    abstract fun attachToolPanel(activity: Activity?)

    /**
     * main activity 创建时回调
     *
     * @param activity
     */
    abstract fun onMainActivityResume(activity: Activity?)

    /**
     * Activity 创建时回调
     *
     * @param activity
     */
    abstract fun onActivityResume(activity: Activity?)

    /**
     * Activity 页面回退的时候回调
     *
     * @param activity
     */
    abstract fun onActivityBackResume(activity: Activity?)
}