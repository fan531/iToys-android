package com.itoys.utils

import android.app.Activity
import android.app.Application
import com.itoys.utils.compat.ScreenSizeCompat
import com.itoys.utils.impl.UtilsActivityLifecycleImpl

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc utils about bridge.
 */
object UtilsBridge {

    fun init(app: Application) {
        UtilsActivityLifecycleImpl.install(app)
    }

    /**
     * Register the status of application changed callback.
     *
     * [callback] The status of application changed callback.
     */
    fun addAppStatusChangeCallback(callback: UtilsActivityLifecycleImpl.IAppStatusChangedCallback) {
        UtilsActivityLifecycleImpl.addAppStatusChangedCallback(callback)
    }

    /**
     * Unregister the status of application changed callback.
     *
     * [callback] The status of application changed callback.
     */
    fun removeAppStatusChangeCallback(callback: UtilsActivityLifecycleImpl.IAppStatusChangedCallback) {
        UtilsActivityLifecycleImpl.removeAppStatusChangedCallback(callback)
    }

    /**
     * Return the top activity in activity's stack.
     */
    fun getTopActivity(): Activity? {
        return UtilsActivityLifecycleImpl.INSTANCE.getTopActivity()
    }

    /**
    * Return the width of screen, in pixel.
    */
    fun getScreenWidth(): Int {
        return ScreenSizeCompat.screenSize(UtilsInitialization.requireApp()).width
    }

    /**
     * Return the height of screen, in pixel.
     */
    fun getScreenHeight(): Int {
        return ScreenSizeCompat.screenSize(UtilsInitialization.requireApp()).height
    }
}