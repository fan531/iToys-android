package com.itoys.kit

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.itoys.expansion.tagName
import com.itoys.kit.core.ActivityLifecycleStatusInfo
import com.itoys.kit.core.IToysKitManager
import com.itoys.kit.core.IToysKitViewManager

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc itoys kit 模块 全局 activity 生命周期回调实现类.
 */
class IToysKitActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    companion object {

        val INSTANCE = IToysKitActivityLifecycleCallbacksImpl()

        private val ignoreActivityClassNames = arrayOf("DisplayLeakActivity", "ToolPanelActivity")

        fun ignoreCurrentActivityIToysView(activity: Activity): Boolean {
            ignoreActivityClassNames.forEach { name ->
                if (activity.javaClass.simpleName == name) {
                    return true
                }
            }

            return false
        }
    }

    private var startedActivityCounts: Int = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        recordActivityLifeCycleStatus(activity, Lifecycle.Event.ON_CREATE)
        if (ignoreCurrentActivityIToysView(activity)) return
    }

    override fun onActivityStarted(activity: Activity) {
        if (ignoreCurrentActivityIToysView(activity)) return

        if (startedActivityCounts == 0) {
            IToysKitViewManager.INSTANCE.notifyForeground()
        }

        startedActivityCounts++
    }

    override fun onActivityResumed(activity: Activity) {
        recordActivityLifeCycleStatus(activity, Lifecycle.Event.ON_RESUME)
        if (ignoreCurrentActivityIToysView(activity)) return
        dispatchOnActivityResumed(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        if (ignoreCurrentActivityIToysView(activity)) return
        IToysKitViewManager.INSTANCE.onActivityPaused(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        recordActivityLifeCycleStatus(activity, Lifecycle.Event.ON_STOP)
        if (ignoreCurrentActivityIToysView(activity)) return
        startedActivityCounts--
        if (startedActivityCounts == 0) {
            IToysKitViewManager.INSTANCE.notifyBackground()
        }
        IToysKitViewManager.INSTANCE.onActivityStopped(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { /* 空实现 */ }

    override fun onActivityDestroyed(activity: Activity) {
        recordActivityLifeCycleStatus(activity, Lifecycle.Event.ON_DESTROY)
        if (ignoreCurrentActivityIToysView(activity)) return
        IToysKitViewManager.INSTANCE.onActivityDestroyed(activity)
    }

    private fun dispatchOnActivityResumed(activity: Activity) {
        activity.window.decorView.also {
            it.post { IToysKitViewManager.windowSize.set(it.width, it.height) }
        }

        IToysKitViewManager.INSTANCE.dispatchOnActivityResumed(activity)
    }

    private fun recordActivityLifeCycleStatus(
        activity: Activity,
        lifecycleState: Lifecycle.Event,
    ) {
        var lifecycleStateInfo = IToysKitManager.ACTIVITY_LIFECYCLE_INFO[activity.tagName]

        if (lifecycleStateInfo == null) {
            lifecycleStateInfo = ActivityLifecycleStatusInfo(
                isInvokeStopMethod = false,
                lifeCycleStatus = lifecycleState,
                activityName = activity.tagName
            )

            IToysKitManager.ACTIVITY_LIFECYCLE_INFO[activity.tagName] = lifecycleStateInfo
        }
        lifecycleStateInfo.lifeCycleStatus = lifecycleState

        when (lifecycleState) {
            Lifecycle.Event.ON_STOP -> {
                lifecycleStateInfo.isInvokeStopMethod = true
            }

            Lifecycle.Event.ON_DESTROY -> {
                IToysKitManager.ACTIVITY_LIFECYCLE_INFO.remove(activity.tagName)
            }

            else -> { /* 空实现 */ }
        }
    }
}