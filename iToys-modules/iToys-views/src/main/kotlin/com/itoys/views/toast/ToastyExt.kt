package com.itoys.views.toast

import androidx.annotation.StringRes
import com.itoys.utils.UtilsInitialization

fun Any.toast(
    @StringRes message: Int,
    duration: Int = Toasty.LENGTH_SHORT,
    status: ToastyStatus? = null
) {
    if (status != null) {
        Toasty.custom(
            UtilsInitialization.requireApp(),
            message,
            duration = duration,
            icon = status.icon,
            tintColor = status.tintColor,
            textColor = status.textColor,
        ).show()
    } else {
        Toasty.normal(
            UtilsInitialization.requireApp(),
            message,
            icon = null,
            duration = duration
        ).show()
    }
}

fun Any.toast(
    message: String,
    duration: Int = Toasty.LENGTH_SHORT,
    status: ToastyStatus? = null
) {
    if (status != null) {
        Toasty.custom(
            UtilsInitialization.requireApp(),
            message,
            duration = duration,
            icon = status.icon,
            tintColor = status.tintColor,
            textColor = status.textColor,
        ).show()
    } else {
        Toasty.normal(
            UtilsInitialization.requireApp(),
            message,
            icon = null,
            duration = duration
        ).show()
    }
}