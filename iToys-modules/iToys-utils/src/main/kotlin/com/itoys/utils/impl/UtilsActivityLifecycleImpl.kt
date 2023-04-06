package com.itoys.utils.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.itoys.expansion.tagName
import com.itoys.expansion.then
import com.itoys.logcat.LogPriority
import com.itoys.logcat.asLog
import com.itoys.logcat.logToFile
import com.itoys.logcat.logcat
import com.itoys.utils.ActivityUtils
import java.util.LinkedList
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc utils 模块 全局activity 生命周期回调实现类.
 */

class UtilsActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {
    companion object {

        private const val CLASS_NAME_ACTIVITY_THREAD = "android.app.ActivityThread"
        private const val FIELD_NAME_ACTIVITY_THREAD = "sCurrentActivityThread"
        private const val METHOD_NAME_ACTIVITY_THREAD = "currentActivityThread"

        val INSTANCE: UtilsActivityLifecycleImpl by lazy { UtilsActivityLifecycleImpl() }

        private val mActivityList: LinkedList<Activity> by lazy { LinkedList() }

        private val mAppStatusList: CopyOnWriteArrayList<IAppStatusChangedCallback> by lazy { CopyOnWriteArrayList() }

        fun install(app: Application) {
            app.registerActivityLifecycleCallbacks(INSTANCE)
        }

        fun uninstall(app: Application) {
            mActivityList.clear()
            app.unregisterActivityLifecycleCallbacks(INSTANCE)
        }

        fun addAppStatusChangedCallback(callback: IAppStatusChangedCallback) {
            mAppStatusList.add(callback)
        }

        fun removeAppStatusChangedCallback(callback: IAppStatusChangedCallback) {
            mAppStatusList.remove(callback)
        }
    }

    private var mIsBackground: Boolean = false

    private var mConfigCount: Int = 0
    private var mForegroundCount: Int = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        logToFile { "${activity.tagName} is created!" }

        if (mActivityList.isEmpty()) {
            postStatus(activity, isForeground = true)
        }

        setTopActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        logcat(LogPriority.INFO) { "${activity.tagName} is started!" }

        if (!mIsBackground) {
            setTopActivity(activity)
        }

        if (mConfigCount < 0) {
            ++mConfigCount
        } else {
            ++mForegroundCount
        }
    }

    override fun onActivityResumed(activity: Activity) {
        logToFile(LogPriority.INFO) { "${activity.tagName} is resumed!" }

        setTopActivity(activity)
        if (mIsBackground) {
            mIsBackground = false
            postStatus(activity, isForeground = true)
        }


    }

    override fun onActivityPaused(activity: Activity) {
        logToFile { "${activity.tagName} is paused!" }
    }

    override fun onActivityStopped(activity: Activity) {
        logToFile { "${activity.tagName} is stopped!" }

        if (activity.isChangingConfigurations) {
            --mConfigCount
        } else {
            --mForegroundCount

            if (mForegroundCount < 0) {
                mIsBackground = true
                postStatus(activity, isForeground = false)
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logcat(LogPriority.INFO) { "${activity.tagName} is save instance state!" }
    }

    override fun onActivityDestroyed(activity: Activity) {
        logToFile { "${activity.tagName} is destroyed!" }

        mActivityList.remove(activity)
    }

    /**
     * 当前 app 是否在前台状态回调
     */
    private fun postStatus(activity: Activity, isForeground: Boolean = true) {
        if (mAppStatusList.isEmpty()) return

        mAppStatusList.forEach { callback ->
            isForeground.then(
                { callback.onForeground(activity) },
                { callback.onBackground(activity) })
        }
    }

    /**
     * 使当前 activity 保持在最上面
     */
    private fun setTopActivity(activity: Activity) {
        if (mActivityList.contains(activity)) {
            if (!mActivityList.first.equals(activity)) {
                mActivityList.remove(activity)
                mActivityList.addFirst(activity)
            }
        } else {
            mActivityList.addFirst(activity)
        }
    }

    fun getTopActivity(): Activity? {
        val activityList = getActivityList()
        activityList.forEach { activity ->
            if (!ActivityUtils.isActivityAlive(activity)) {
                return@forEach
            }

            return activity
        }

        return null
    }

    private fun getActivityList(): List<Activity> {
        if (mActivityList.isNotEmpty()) {
            return LinkedList(mActivityList)
        }

        val reflectActivityList = getActivityListByReflect()
        mActivityList.addAll(reflectActivityList)
        return LinkedList(mActivityList)
    }

    private fun getActivityListByReflect(): List<Activity> {
        val list: LinkedList<Activity> = LinkedList()
        var topActivity: Activity? = null

        try {
            val activityThread = getActivityThread()
            val mActivitiesField = activityThread?.javaClass?.getDeclaredField("mActivities")
            mActivitiesField?.isAccessible = true
            val mActivities = mActivitiesField?.get(activityThread)
            if (mActivities !is Map<*, *>) return list

            mActivities.values.forEach {  activityRecord ->
                val activityRecordClass = activityRecord?.javaClass
                val activityField = activityRecordClass?.getDeclaredField("activity")
                activityField?.isAccessible = true
                val activity = activityField?.get(activityRecordClass) as Activity?

                if (topActivity == null) {
                    val pauseField = activityRecordClass?.getDeclaredField("paused")
                    pauseField?.isAccessible = true
                    if (pauseField?.getBoolean(activityRecord) == false) {
                        topActivity = activity
                    } else {
                        activity?.let { list.add(activity) }
                    }
                } else {
                    activity?.let { list.add(activity) }
                }
            }
        } catch (e: Exception) {
            logcat(priority = LogPriority.ERROR) { e.asLog() }
        }

        if (topActivity != null) list.addFirst(topActivity)

        return list
    }

    /**
     * Return activity thread by reflect.
     */
    @SuppressLint("PrivateApi")
    private fun getActivityThread(): Any? {
        return try {
            val activityThreadClass = Class.forName(CLASS_NAME_ACTIVITY_THREAD)
            val activityThread = getActivityThreadInActivityThreadStaticField(activityThreadClass)
            (activityThread != null).then({ activityThread },
                { getActivityThreadInActivityThreadStaticMethod(activityThreadClass) })
        } catch (e: Exception) {
            logcat(priority = LogPriority.ERROR) { e.asLog() }
            null
        }
    }

    /**
     * Return activity thread by reflect field.
     */
    private fun getActivityThreadInActivityThreadStaticField(activityThread: Class<*>): Any? {
        return try {
            val field = activityThread.getDeclaredField(FIELD_NAME_ACTIVITY_THREAD)
            field.isAccessible = true
            field.get(null)
        } catch (e: Exception) {
            logcat(priority = LogPriority.ERROR) { e.asLog() }
            null
        }
    }

    /**
     * Return activity thread by reflect method.
     */
    private fun getActivityThreadInActivityThreadStaticMethod(activityThread: Class<*>): Any? {
        return try {
            activityThread.getMethod(METHOD_NAME_ACTIVITY_THREAD).invoke(null)
        } catch (e: Exception) {
            logcat(priority = LogPriority.ERROR) { e.asLog() }
            null
        }
    }

    interface IAppStatusChangedCallback {

        /**
         * 回到前台
         */
        fun onForeground(activity: Activity)

        /**
         * 回到后台
         */
        fun onBackground(activity: Activity)
    }
}