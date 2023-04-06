package com.itoys.utils

import android.app.Activity

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc
 */
object ActivityUtils {

    fun getTopActivity(): Activity? {
        return UtilsBridge.getTopActivity()
    }

    /**
     * Return whether the activity is alive.
     */
    fun isActivityAlive(activity: Activity?): Boolean {
        return activity != null && !activity.isFinishing && !activity.isDestroyed
    }

    /**
     * Return Whether or not system main activity.
     */
    fun isMainLaunchActivity(activity: Activity): Boolean {
        val packageManager = UtilsInitialization.requireApp().packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(activity.packageName) ?: return false
        val launchComponentName = launchIntent.component
        val componentName = activity.componentName

        return (launchComponentName != null && componentName.equals(launchComponentName))
    }
}