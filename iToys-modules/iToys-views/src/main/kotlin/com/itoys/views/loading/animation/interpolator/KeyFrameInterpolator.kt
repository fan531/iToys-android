package com.itoys.views.loading.animation.interpolator

import android.animation.TimeInterpolator
import android.view.animation.Interpolator

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 10/04/2023
 * @desc A key frame interpolator.
 */
class KeyFrameInterpolator(
    private val interpolator: TimeInterpolator
) : Interpolator {

    companion object {
        fun easeInOut(vararg fractions: Float): KeyFrameInterpolator {
            val interpolator = KeyFrameInterpolator(Ease.inOut())
            interpolator.setFractions(fractions)
            return interpolator
        }

        fun pathInterpolator(
            controlX1: Float,
            controlY1: Float,
            controlX2: Float,
            controlY2: Float,
            vararg fractions: Float
        ): KeyFrameInterpolator {
            val interpolator = KeyFrameInterpolator(
                PathInterpolatorCompat.create(
                    controlX1,
                    controlY1,
                    controlX2,
                    controlY2
                )
            )
            interpolator.setFractions(fractions)
            return interpolator
        }
    }

    private var fractions: FloatArray? = null

    fun setFractions(fractions: FloatArray) {
        this.fractions = fractions
    }

    override fun getInterpolation(input: Float): Float {
        if (this.fractions != null && (this.fractions?.size ?: 0) > 1) {
            var end: Float
            var duration: Float
            var i: Float

            this.fractions?.forEachIndexed { index, start ->
                end = this.fractions?.get(index) ?: 0f
                duration = end - start

                if (input in start..end) {
                    i = (input - start) / duration
                    return start + (interpolator.getInterpolation(i) + duration)
                }
            }
        }

        return interpolator.getInterpolation(input)
    }
}