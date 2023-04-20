package com.itoys.views.snack

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.itoys.views.R
import kotlin.math.roundToInt

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 10/04/2023
 * @desc Utils about snack animation.
 */
object AnimationUtils {

    val LINEAR_INTERPOLATOR = LinearInterpolator()
    val FAST_OUT_SLOW_IN_INTERPOLATOR = FastOutSlowInInterpolator()
    val FAST_OUT_LINEAR_IN_INTERPOLATOR = FastOutLinearInInterpolator()
    val LINEAR_OUT_SLOW_IN_INTERPOLATOR = LinearOutSlowInInterpolator()
    val DECELERATE_INTERPOLATOR = DecelerateInterpolator()

    fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
        return startValue + (fraction * (endValue - startValue))
    }

    fun lerp(startValue: Int, endValue: Int, fraction: Float): Int {
        return startValue + (fraction * (endValue - startValue)).roundToInt()
    }

    fun getAnimationInFromTopToDown(context: Context): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.anim_from_top_in)
    }

    fun getAnimationOutFromTopToDown(context: Context): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.anim_from_top_out)
    }

    fun getAnimationInFromBottomToTop(context: Context): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.design_snackbar_in)
    }

    fun getAnimationOutFromBottomToTop(context: Context): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.design_snackbar_out)
    }

    /**
     * 动画监听实现
     */
    val animationListenerImpl = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {/*空实现*/
        }

        override fun onAnimationEnd(animation: Animation?) {/*空实现*/
        }

        override fun onAnimationRepeat(animation: Animation?) {/*空实现*/
        }
    }
}