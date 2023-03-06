package com.itoys.expansion

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

/**
 * @author Fanfan Gu <a href="mailto:stefan.gufan@gmail.com">Contact me.</a>
 * @date 25/04/2022 01:03
 * @desc Context扩展.
 */

/**
 * 根据颜色资源id获取颜色
 */
fun Context.color(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

/**
 * 根据drawable资源id获取drawable
 */
fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableResId)
}

fun View.backgroundExt(drawable: Drawable?) {
    if (SysExpansion.isAndroidJelly()) {
        this.background = drawable
    } else {
        this.setBackgroundDrawable(drawable)
    }
}

fun Drawable.tintIcon(@ColorInt tintColor: Int): Drawable {
    this.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    return this
}

/**
 * dp转px
 *
 * @param dpValue dp值
 * @return px值
 */
fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * px转dp
 *
 * @param pxValue px值
 * @return dp值
 */
fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * sp转px
 *
 * @param spValue sp值
 * @return px值
 */
fun Context.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * px转sp
 *
 * @param pxValue px值
 * @return sp值
 */
fun Context.px2sp(pxValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}