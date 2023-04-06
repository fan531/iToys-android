package com.itoys.kit.core

import android.app.Activity
import android.os.Bundle
import com.itoys.expansion.tagName
import com.itoys.utils.ActivityUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 28/03/2023
 * @desc
 */
data class IToysKitIntent(
    var targetClass: Class<out AbsIToysKitView>,
    var activity: Activity? = ActivityUtils.getTopActivity(),
    var bundle: Bundle? = null,
    var tag: String = targetClass.tagName,
    var mode: IToysKitViewLaunchMode = IToysKitViewLaunchMode.SINGLE_INSTANCE
)

enum class IToysKitViewLaunchMode {
    SINGLE_INSTANCE, COUNTDOWN
}