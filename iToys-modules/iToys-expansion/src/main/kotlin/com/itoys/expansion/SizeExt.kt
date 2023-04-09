package com.itoys.expansion

import android.content.res.Resources

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 28/03/2023
 * @desc expansion about size
 */

/**
 * dp转px
 */
fun Int?.dp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0) * scale + 0.5f).toInt()
}

/**
 * px转dp
 */
fun Int?.px2dp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0) * scale + 0.5f).toInt()
}

/**
 * sp转px
 */
fun Int?.sp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0) * scale + 0.5f).toInt()
}

/**
 * px转sp
 */
fun Int?.px2sp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0) * scale + 0.5f).toInt()
}

/**
 * dp转px
 */
fun Float?.dp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0f) * scale + 0.5f).toInt()
}

/**
 * px转dp
 */
fun Float?.px2dp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0f) * scale + 0.5f).toInt()
}

/**
 * sp转px
 */
fun Float?.sp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0f) * scale + 0.5f).toInt()
}

/**
 * px转sp
 */
fun Float?.px2sp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return ((this ?: 0f) * scale + 0.5f).toInt()
}