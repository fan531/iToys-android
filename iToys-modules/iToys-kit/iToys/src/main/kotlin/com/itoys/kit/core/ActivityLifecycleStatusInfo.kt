package com.itoys.kit.core

import androidx.lifecycle.Lifecycle

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc activity 状态信息.
 */
data class ActivityLifecycleStatusInfo(
    var isInvokeStopMethod: Boolean? = false,
    var activityName: String? = "",
    var lifeCycleStatus: Lifecycle.Event? = Lifecycle.Event.ON_ANY,
)
