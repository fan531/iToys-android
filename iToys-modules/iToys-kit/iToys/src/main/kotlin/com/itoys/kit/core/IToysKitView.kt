package com.itoys.kit.core

import android.content.Context
import android.view.View
import android.widget.FrameLayout

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 26/03/2023
 * @desc 页面浮标接口
 */
interface IToysKitView {

    /**
     * 创建时调用 做一些变量的初始化  当然还不能进行View的操作
     */
    fun onCreate(context: Context)

    /**
     * 用于创建kit控件
     */
    fun onCreateView(context: Context, rootView: FrameLayout?): View

    /**
     * 将xml中的控件添加到rootView以后调用，在当前方法中可以进行view的一些操作
     */
    fun onViewCreated(rootView: FrameLayout)

    /**
     * 当前的view添加到根布局里时调用
     */
    fun onResume()

    /**
     * 当前activity onPause时调用
     */
    fun onPause()

    /**
     * 确定系统悬浮窗浮标的初始位置
     */
    fun initIToysViewLayoutParams(params: IToysKitViewLayoutParams)

    /**
     * app进入后台时调用
     */
    fun onEnterBackground()

    /**
     * app回到前台时调用
     */
    fun onEnterForeground()

    /**
     * 浮标控件是否可以拖动
     */
    fun canDrag(): Boolean

    /**
     * 是否需要自己处理返回键
     */
    fun shouldDealBackKey(): Boolean

    /**
     * [shouldDealBackKey] == true 时调用
     */
    fun onBackPressed(): Boolean

    /**
     * 悬浮窗主动销毁时调用
     */
    fun onDestroy()
}