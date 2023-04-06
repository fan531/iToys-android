package com.itoys.kit.core

import android.app.Activity
import android.content.Context
import android.util.ArrayMap
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import com.itoys.expansion.isBlank
import com.itoys.expansion.tagName
import com.itoys.kit.IToysKitReal
import com.itoys.kit.R
import com.itoys.kit.tool.ui.ToolPanelActivity
import com.itoys.kit.util.IToysKitSysUtil

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc 每个activity悬浮窗管理类
 */
internal class NormalIToysKitViewManager : AbsIToysKitViewManager() {

    companion object {
        const val DELAYED_TIME = 100L
    }

    /**
     * 每个Activity中kitView的集合 用户手动移除和页面销毁时都需要remove
     */
    private val activityIToysKitViewMap: MutableMap<Activity, MutableMap<String, AbsIToysKitView>> = ArrayMap()

    /**
     * 只用来记录全局的同步 只有用户手动移除时才会remove
     */
    private val globalIToysKitViewInfoMap: MutableMap<String, GlobalSingleIToysKitViewInfo> = ArrayMap()

    private val context: Context get() = IToysKitReal.requireApp()

    override fun attachMainIcon(activity: Activity?) {
        if (activity == null) return

        if (IToysKitManager.ALWAYS_SHOW_ENTER_ICON) {
            attach(IToysKitIntent(MainIconKitView::class.java))
            IToysKitManager.ENTER_ICON_HAS_SHOW = true
        } else {
            IToysKitManager.ENTER_ICON_HAS_SHOW = false
        }
    }

    override fun detachMainIcon() {
        detach(MainIconKitView::class.tagName)
    }

    override fun attachToolPanel(activity: Activity?) {
        activity?.apply { ToolPanelActivity.open(this) }
    }

    override fun onMainActivityResume(activity: Activity?) {
        if (activity == null) return

        // 假如不存在全局的icon这需要全局显示主icon
        attachMainIcon(activity)
    }

    override fun onActivityResume(activity: Activity?) {
        if (activity == null) return

        globalIToysKitViewInfoMap.values.forEach { kitViewInfo ->
            if (!IToysKitManager.ALWAYS_SHOW_ENTER_ICON && kitViewInfo.tag == MainIconKitView::class.tagName) {
                IToysKitManager.ALWAYS_SHOW_ENTER_ICON = false
                return@forEach
            }

            if (kitViewInfo.tag == MainIconKitView::class.tagName) {
                IToysKitManager.ALWAYS_SHOW_ENTER_ICON = true
            }

            attachKitView(kitViewInfo)
        }

        // 假如不存在全局的icon这需要全局显示主icon
        attachMainIcon(activity)
    }

    override fun onActivityBackResume(activity: Activity?) {
        if (activity == null) return

        val existKitViews: Map<String, AbsIToysKitView>? = activityIToysKitViewMap[activity]
        globalIToysKitViewInfoMap.values.forEach { kitViewInfo ->
            if (!IToysKitManager.ALWAYS_SHOW_ENTER_ICON && kitViewInfo.tag == MainIconKitView::class.tagName) {
                IToysKitManager.ALWAYS_SHOW_ENTER_ICON = false
                return@forEach
            }

            if (kitViewInfo.tag == MainIconKitView::class.tagName) {
                IToysKitManager.ALWAYS_SHOW_ENTER_ICON = true
            }

            val existKitView: AbsIToysKitView? = existKitViews?.get(kitViewInfo.tag)
            if (existKitView?.kitView == null) {
                attachKitView(kitViewInfo)
            } else {
                existKitView.kitView?.visibility = View.VISIBLE
                existKitView.updateViewLayout(existKitView.tag, isActivityBackResume = true)
                existKitView.onResume()
            }
        }

        // 假如不存在全局的icon这需要全局显示主icon
        attachMainIcon(activity)
    }

    override fun attach(itoysKitIntent: IToysKitIntent) {
        // 判断当前Activity是否存在 KitView map
        var kitViews = activityIToysKitViewMap[itoysKitIntent.activity]
        if (kitViews == null) {
            kitViews = mutableMapOf()
            itoysKitIntent.activity?.let { activity -> activityIToysKitViewMap[activity] = kitViews }
        }

        // 判断 KitView 是否已经显示在页面上, 同一类型的 KitView 在页面上只能显示一个
        if (kitViews.containsKey(itoysKitIntent.tag)) {
            kitViews[itoysKitIntent.tag]?.updateViewLayout(
                itoysKitIntent.tag,
                isActivityBackResume = true
            )
            return
        }

        val kitView = itoysKitIntent.targetClass.newInstance()
        kitView.mode = itoysKitIntent.mode
        kitView.bundle = itoysKitIntent.bundle
        kitView.tag = itoysKitIntent.tag
        kitView.setActivity(itoysKitIntent.activity)
        kitView.performCreate(context)
        // 在全局 kit views 中保存该类型 kit view
        globalIToysKitViewInfoMap[kitView.tag] = mapperGlobalSingleInfo(kitView)
        if (kitView.mNormalLayoutParams != null && kitView.kitView != null) {
            getKitRootContentView(kitView.activity)?.addView(kitView.kitView, kitView.mNormalLayoutParams)
            kitView.kitView?.postDelayed({ kitView.onResume() }, DELAYED_TIME)
        }
        kitViews[kitView.tag] = kitView
    }

    /**
     * Return kit roor view.
     */
    private fun getKitRootContentView(activity: Activity?): FrameLayout? {
        val decorView = (activity?.window?.decorView as ViewGroup?) ?: return null
        var kitRootView = decorView.findViewById(R.id.id_kit_content_view) as FrameLayout?
        if (kitRootView != null) return kitRootView

        kitRootView = IToysKitFrameLayout(context)
        kitRootView.id = R.id.id_kit_content_view
        kitRootView.clipChildren = false
        kitRootView.clipToPadding = false
        kitRootView.isFocusable = true
        kitRootView.isFocusableInTouchMode = true
        kitRootView.requestFocus()

        val kitRootViewParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
        ).apply {
            gravity = Gravity.BOTTOM
        }
        kitRootView.layoutParams = kitRootViewParams
        decorView.addView(kitRootView)
        return kitRootView
    }

    override fun detach(itoysKitView: AbsIToysKitView) {
        realDetach(itoysKitView.tag)
    }

    override fun detach(tag: String) {
        realDetach(tag)
    }

    override fun detach(itoysKitViewClass: Class<out AbsIToysKitView>) {
        realDetach(itoysKitViewClass.tagName)
    }

    /**
     * 移除每个activity指定的itoysKitView tag
     */
    private fun realDetach(tag: String) {
        activityIToysKitViewMap.keys.forEach { activityKey ->
            val kitViewMap = activityIToysKitViewMap[activityKey]
            val itoysKitView = kitViewMap?.get(tag) ?: return@forEach
            if (itoysKitView.kitView != null) {
                itoysKitView.kitView?.visibility = View.GONE
                getKitRootContentView(itoysKitView.activity)?.removeView(itoysKitView.kitView)
                // 移除指定UI后请求重新绘制
                (activityKey.window.decorView as ViewGroup?)?.requestLayout()
                itoysKitView.performDestroy()
                kitViewMap.remove(tag)
            }
        }
    }

    override fun detachAll() {
        activityIToysKitViewMap.keys.forEach { activityKey ->
            val kitViewMap = activityIToysKitViewMap[activityKey]
            getKitRootContentView(activityKey)?.removeAllViews()
            kitViewMap?.clear()
        }
        globalIToysKitViewInfoMap.clear()
    }

    override fun <T : AbsIToysKitView> getIToysKitView(
        activity: Activity?,
        clazz: Class<T>
    ): AbsIToysKitView? {
        if (activity == null) return null
        if (clazz.tagName.isBlank()) return null
        return activityIToysKitViewMap[activity]?.get(clazz.tagName)
    }

    override fun getIToysKitViews(activity: Activity?): Map<String, AbsIToysKitView> {
        if (activity == null) return emptyMap()
        return activityIToysKitViewMap[activity] ?: emptyMap()
    }

    override fun notifyForeground() {
        activityIToysKitViewMap.forEach { maps ->
            maps.value.forEach { map ->
                map.value.onEnterForeground()
            }
        }
    }

    override fun notifyBackground() {
        activityIToysKitViewMap.forEach { maps ->
            maps.value.forEach { map ->
                map.value.onEnterBackground()
            }
        }
    }

    override fun dispatchOnActivityResumed(activity: Activity?) {
        if (activity == null) return

        if (IToysKitSysUtil.isOnlyFirstLaunchActivity(activity)) {
            onMainActivityResume(activity)
            return
        }

        IToysKitManager.ACTIVITY_LIFECYCLE_INFO[activity.tagName]?.let { statusInfo ->
            if (statusInfo.lifeCycleStatus == Lifecycle.Event.ON_RESUME) {
                when (statusInfo.isInvokeStopMethod) {
                    true -> onActivityBackResume(activity)
                    false -> onActivityResume(activity)
                    else -> { /* 空实现*/ }
                }
            }
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        if (activity == null) return
        getIToysKitViews(activity).values.forEach { view ->
            view.onPause()
        }
    }

    override fun onActivityStopped(activity: Activity?) { /*空实现*/ }

    override fun onActivityDestroyed(activity: Activity?) {
        if (activity == null) return

        val rootView = activity.findViewById(R.id.id_kit_content_view) as View?
        if (rootView != null) {
            (activity.window.decorView as ViewGroup?)?.removeView(rootView)
        }
        val kitViewMap = getIToysKitViews(activity)
        kitViewMap.values.forEach { kitView ->
            kitView.performDestroy()
        }
        activityIToysKitViewMap.remove(activity)
    }

    /**
     * 在当前Activity中添加指定悬浮窗
     */
    private fun attachKitView(kitViewInfo: GlobalSingleIToysKitViewInfo) {
        val kitIntent = IToysKitIntent(kitViewInfo.kitViewClass)
        kitIntent.bundle = kitViewInfo.bundle
        kitIntent.mode = kitViewInfo.mode
        kitIntent.tag = kitViewInfo.tag
        attach(kitIntent)
    }

    /**
     * Return [GlobalSingleIToysKitViewInfo]
     */
    private fun mapperGlobalSingleInfo(kitView: AbsIToysKitView) : GlobalSingleIToysKitViewInfo {
        return GlobalSingleIToysKitViewInfo(
            kitView.javaClass,
            kitView.tag,
            kitView.mode,
            kitView.bundle
        )
    }
}