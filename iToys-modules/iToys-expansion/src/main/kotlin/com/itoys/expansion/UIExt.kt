package com.itoys.expansion

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/03/2023
 * @desc ui ktx 扩展
 */
/**
 * 颜色
 */
fun Context.color(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

/**
 * 根据drawable资源id获取drawable
 *
 * 详见：[地址](https://stackoverflow.com/questions/43004886/resourcescompat-getdrawable-vs-appcompatresources-getdrawable)
 */
fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableResId)
}

fun Context.tint9PatchDrawable(@DrawableRes drawableResId: Int): NinePatchDrawable? {
    return AppCompatResources.getDrawable(this, drawableResId) as NinePatchDrawable?
}

fun View.backgroundExt(drawable: Drawable?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        this.background = drawable
    } else {
        this.setBackgroundDrawable(drawable)
    }
}

fun Drawable.tintIcon(@ColorInt tintColor: Int): Drawable {
    this.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    return this
}