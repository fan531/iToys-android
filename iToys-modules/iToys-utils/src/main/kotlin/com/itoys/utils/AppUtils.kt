package com.itoys.utils

import com.itoys.utils.impl.UtilsActivityLifecycleImpl

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc utils about app.
 */
object AppUtils {

    /**
     * Register the status of application change callback.
     *
     * [callback] The status of application change callback.
     */
    fun registerAppStatusChangeCallback(callback: UtilsActivityLifecycleImpl.IAppStatusChangedCallback) {
        UtilsBridge.addAppStatusChangeCallback(callback)
    }

    /**
     * Unregister the status of application change callback.
     *
     * [callback] The status of application change callback.
     */
    fun unregisterAppStatusChangeCallback(callback: UtilsActivityLifecycleImpl.IAppStatusChangedCallback) {
        UtilsBridge.removeAppStatusChangeCallback(callback)
    }
}