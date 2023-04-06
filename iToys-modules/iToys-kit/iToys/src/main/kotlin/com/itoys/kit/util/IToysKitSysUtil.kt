package com.itoys.kit.util

import android.app.Activity
import com.itoys.expansion.tagName
import com.itoys.kit.core.IToysKitManager
import com.itoys.utils.ActivityUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc itoys kit utils
 */
object IToysKitSysUtil {

    /**
     * 是否是系统启动第一次调用mainActivity 页面回退不算
     */
    fun isOnlyFirstLaunchActivity(activity: Activity): Boolean {
        val isMainActivity = ActivityUtils.isMainLaunchActivity(activity)
        val lifecycleStatusInfo = IToysKitManager.ACTIVITY_LIFECYCLE_INFO[activity.tagName]
        return isMainActivity && lifecycleStatusInfo != null && lifecycleStatusInfo.isInvokeStopMethod == false
    }
}