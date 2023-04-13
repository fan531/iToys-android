package com.itoys.views.snack

import android.app.Activity
import androidx.fragment.app.Fragment
import com.itoys.expansion.color
import com.itoys.expansion.drawable
import com.itoys.views.snack.TopSnackUtils.snackCallback
import com.itoys.views.snack.TopSnackUtils.topSnackBar

object TopSnackUtils {
    var topSnackBar: TopSnackBar? = null

    val snackCallback : TopSnackBar.Callback by lazy {
        object : TopSnackBar.Callback() {
            override fun onDismissed(snackBar: TopSnackBar?, event: Int) {
                super.onDismissed(snackBar, event)
                topSnackBar = null
            }
        }
    }
}



fun Activity.makeSnack(
    message: String,
    duration: Int = TopSnackBar.LENGTH_SHORT,
    appearDirection: Int = TopSnackBar.APPEAR_FROM_BOTTOM_TO_TOP,
    withLoading: Boolean = false,
    prompt: Prompt? = null
) {
    topSnackBar = TopSnackBar.make(window.decorView, message, duration, appearDirection)
    topSnackBar?.setCallback(snackCallback)
    prompt?.let { p ->
        topSnackBar?.setBackgroundColor(color(p.backgroundColor))
        topSnackBar?.addIcon(drawable(p.icon))
    }
    if (withLoading) {
        topSnackBar?.addLoadingIcon()
    }
    topSnackBar?.show()
}

fun Fragment.makeSnack(
    message: String,
    duration: Int = TopSnackBar.LENGTH_SHORT,
    appearDirection: Int = TopSnackBar.APPEAR_FROM_BOTTOM_TO_TOP,
    withLoading: Boolean = false,
    prompt: Prompt? = null
) {
    activity?.makeSnack(message, duration, appearDirection, withLoading, prompt)
}