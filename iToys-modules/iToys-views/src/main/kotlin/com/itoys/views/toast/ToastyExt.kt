package com.itoys.views.toast

import android.content.Context
import androidx.annotation.StringRes
import com.itoys.utils.UtilsInitialization

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc
 */

fun Any.normalToast(@StringRes message: Int, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().normalToast(message, duration)
}

fun Any.normalToast(message: String, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().normalToast(message, duration)
}

fun Context.normalToast(@StringRes message: Int, duration: Int) {
    Toasty.normal(this, message, duration = duration).show()
}

fun Context.normalToast(message: String, duration: Int) {
    Toasty.normal(this, message, duration = duration).show()
}

fun Any.infoToast(@StringRes message: Int, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().infoToast(message, duration)
}

fun Any.infoToast(message: String, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().infoToast(message, duration)
}

fun Context.infoToast(@StringRes message: Int, duration: Int) {
    Toasty.info(this, message, duration = duration).show()
}

fun Context.infoToast(message: String, duration: Int) {
    Toasty.info(this, message, duration = duration).show()
}

fun Any.warningToast(@StringRes message: Int, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().warningToast(message, duration)
}

fun Any.warningToast(message: String, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().warningToast(message, duration)
}

fun Context.warningToast(@StringRes message: Int, duration: Int) {
    Toasty.warning(this, message, duration = duration).show()
}

fun Context.warningToast(message: String, duration: Int) {
    Toasty.warning(this, message, duration = duration).show()
}

fun Any.successToast(@StringRes message: Int, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().successToast(message, duration)
}

fun Any.successToast(message: String, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().successToast(message, duration)
}

fun Context.successToast(@StringRes message: Int, duration: Int) {
    Toasty.success(this, message, duration = duration).show()
}

fun Context.successToast(message: String, duration: Int) {
    Toasty.success(this, message, duration = duration).show()
}

fun Any.errorToast(@StringRes message: Int, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().errorToast(message, duration)
}

fun Any.errorToast(message: String, duration: Int = Toasty.LENGTH_SHORT) {
    UtilsInitialization.requireApp().errorToast(message, duration)
}

fun Context.errorToast(@StringRes message: Int, duration: Int) {
    Toasty.error(this, message, duration = duration).show()
}

fun Context.errorToast(message: String, duration: Int) {
    Toasty.error(this, message, duration = duration).show()
}