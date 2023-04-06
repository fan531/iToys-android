package com.itoys.utils

import android.content.res.Configuration
import android.graphics.Point
import com.itoys.expansion.displayManager
import com.itoys.expansion.windowService
import com.itoys.utils.compat.ScreenSizeCompat

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 30/03/2023
 * @desc utils about screen.
 */
object ScreenUtils {

    /**
     * Return the width of screen, in pixel.
     */
    fun getScreenWidth(): Int {
        return UtilsBridge.getScreenWidth()
    }

    /**
     * Return the height of screen, in pixel.
     */
    fun getScreenHeight(): Int {
        return UtilsBridge.getScreenHeight()
    }

    /**
     * Return whether screen is portrait.
     */
    fun isPortrait(): Boolean {
        return UtilsInitialization.requireApp().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }
}