package com.itoys.base.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.itoys.logcat.logToFile

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 12/03/2023
 * @desc activity 生命周期回调.
 */
class IToysActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        logToFile(tag = activity.javaClass.canonicalName) { "activityCreated!" }
    }

    override fun onActivityStarted(activity: Activity) {
        logToFile(tag = activity.javaClass.canonicalName) { "activityStarted!" }
    }

    override fun onActivityResumed(activity: Activity) {
        logToFile(tag = activity.javaClass.canonicalName) { "activityResumed!" }
    }

    override fun onActivityPaused(activity: Activity) {
        logToFile(tag = activity.javaClass.canonicalName) { "activityPaused!" }
    }

    override fun onActivityStopped(activity: Activity) {
        logToFile(tag = activity.javaClass.canonicalName) { "activityStopped!" }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logToFile(tag = activity.javaClass.canonicalName) { "activitySaveInstanceState!" }
    }

    override fun onActivityDestroyed(activity: Activity) {
        logToFile(tag = activity.javaClass.canonicalName) { "activityDestroyed!" }
    }
}