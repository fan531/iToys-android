package com.itoys.views.snack

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 12/04/2023
 * @desc
 */
private var snackBar: TopSnackBar? = null

fun Activity.makeSnack(message: String) {
    snackBar = TopSnackBar.make(window.decorView, message, TopSnackBar.LENGTH_SHORT)
    snackBar?.show()
}

fun Fragment.makeSnack(message: String) {
    activity?.let {
        snackBar = TopSnackBar.make(it.window.decorView, message, TopSnackBar.LENGTH_SHORT)
        snackBar?.show()
    }
}