package com.itoys.views.snack

import android.view.View
import android.view.ViewGroup

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 11/04/2023
 * @desc Utils about snack.
 */
object SnackUtils {

    fun setMargins(view: View?, l: Int, t: Int, r: Int, b: Int) {
        val layoutParams = view?.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            layoutParams.setMargins(l, t, r, b)
            view.requestLayout()
        }
    }
}