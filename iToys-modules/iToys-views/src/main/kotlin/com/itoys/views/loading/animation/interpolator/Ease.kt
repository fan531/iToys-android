package com.itoys.views.loading.animation.interpolator

import android.view.animation.Interpolator

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc interpolator about ease.
 */
object Ease {

    fun inOut() : Interpolator {
        return PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f)
    }
}