package com.itoys.utils

import android.util.TypedValue


/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 11/04/2023
 * @desc Utils about bar.
 */
object BarUtils {

    /**
     * Return the status bar's height.
     */
    fun getStatusBarHeight(): Int {
        return try {
            val resources = UtilsInitialization.requireApp().resources
            val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
            resources.getDimensionPixelSize(resourceId)
        } catch (e: Exception) {
            -1
        }
    }

    fun getActionBarHeight(): Int {
        return try {
            val typedValue = TypedValue()
            val resources = UtilsInitialization.requireApp().resources

            if (UtilsInitialization.requireApp().theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
            } else {
                -1
            }
        } catch (e: Exception) {
            -1
        }
    }
}