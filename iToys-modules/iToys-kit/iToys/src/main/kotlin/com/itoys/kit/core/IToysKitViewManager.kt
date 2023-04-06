package com.itoys.kit.core

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Point
import android.util.ArrayMap
import android.view.WindowManager
import com.itoys.expansion.windowService
import com.itoys.kit.IToysKitReal
import com.itoys.utils.ScreenUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 26/03/2023
 * @desc 页面浮标管理类
 */
internal class IToysKitViewManager : IToysKitViewManagerInterface {

    companion object {
        val INSTANCE: IToysKitViewManager by lazy { IToysKitViewManager() }

        val windowSize: Point = Point()

        private val kitViewInfoMaps: MutableMap<String, IToysKitViewInfo> = ArrayMap()
        private val kitViewPositionInfoMaps: MutableMap<String, IToysKitViewPositionInfo> = ArrayMap()
    }

    /**
     * WindowManager
     */
    val windowManager: WindowManager?
        get() = IToysKitReal.requireApp().windowService

    private var _kitViewManager: AbsIToysKitViewManager? = null

    override fun attach(itoysKitIntent: IToysKitIntent) {
        ensureViewManager().attach(itoysKitIntent)
    }

    override fun detach(itoysKitView: AbsIToysKitView) {
        ensureViewManager().detach(itoysKitView)
    }

    override fun detach(tag: String) {
        ensureViewManager().detach(tag)
    }

    override fun detach(itoysKitViewClass: Class<out AbsIToysKitView>) {
        ensureViewManager().detach(itoysKitViewClass)
    }

    override fun detachAll() {
        ensureViewManager().detachAll()
    }

    override fun <T : AbsIToysKitView> getIToysKitView(
        activity: Activity?,
        clazz: Class<T>
    ): AbsIToysKitView? = ensureViewManager().getIToysKitView(activity, clazz)

    override fun getIToysKitViews(activity: Activity?): Map<String, AbsIToysKitView> = ensureViewManager().getIToysKitViews(activity)

    override fun notifyForeground() {
        ensureViewManager().notifyForeground()
    }

    override fun notifyBackground() {
        ensureViewManager().notifyBackground()
    }

    override fun dispatchOnActivityResumed(activity: Activity?) {
        ensureViewManager().dispatchOnActivityResumed(activity)
    }

    override fun onActivityPaused(activity: Activity?) {
        ensureViewManager().onActivityPaused(activity)
    }

    override fun onActivityStopped(activity: Activity?) {
        ensureViewManager().onActivityStopped(activity)
    }

    override fun onActivityDestroyed(activity: Activity?) {
        ensureViewManager().onActivityDestroyed(activity)
    }

    fun getKitViewPosition(tag: String): IToysKitViewInfo? =  kitViewInfoMaps[tag]

    /**
     * 保存 kit view 的位置
     */
    fun saveKitViewPosition(tag: String, marginLeft: Int, marginTop: Int) {
        val orientation: Int
        val portraitPoint = Point()
        val landscapePoint = Point()

        if (ScreenUtils.isPortrait()) {
            orientation = Configuration.ORIENTATION_PORTRAIT
            portraitPoint.x = marginLeft
            portraitPoint.y = marginTop
        } else {
            orientation = Configuration.ORIENTATION_LANDSCAPE
            landscapePoint.x = marginLeft
            landscapePoint.y = marginTop
        }

        var kitViewInfo = kitViewInfoMaps[tag]

        if (kitViewInfo == null) {
            kitViewInfo = IToysKitViewInfo(orientation, portraitPoint, landscapePoint)
        }

        kitViewInfo.orientation = orientation
        kitViewInfo.portraitPoint = portraitPoint
        kitViewInfo.landscapePoint = landscapePoint

        kitViewInfoMaps[tag] = kitViewInfo
    }

    fun saveKitViewPositionInfo(key: String, positionInfo: IToysKitViewPositionInfo) {
        kitViewPositionInfoMaps[key] = positionInfo
    }

    fun getKitViewPositionInfo(key: String): IToysKitViewPositionInfo? = kitViewPositionInfoMaps[key]

    fun removeKitViewPositionInfo(key: String) {
        if (kitViewPositionInfoMaps.containsKey(key)) {
            kitViewPositionInfoMaps.remove(key)
        }
    }

    /**
     * 显示工具列表
     */
    fun attachToolPanel(activity: Activity?) {
        ensureViewManager().attachToolPanel(activity)
    }

    private fun ensureViewManager(): AbsIToysKitViewManager {
        return _kitViewManager ?: run { NormalIToysKitViewManager() }.also { _kitViewManager = it }
    }
}