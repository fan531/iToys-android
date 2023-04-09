package com.itoys.views.loading

import android.animation.Animator
import android.animation.ValueAnimator

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc utils about animation.
 */
object AnimationUtils {

    fun start(animator: Animator?) {
        if (animator != null && !animator.isStarted) {
            animator.start()
        }
    }

    fun stop(animator: Animator?) {
        if (animator != null && !animator.isRunning) {
            animator.end()
        }
    }
}