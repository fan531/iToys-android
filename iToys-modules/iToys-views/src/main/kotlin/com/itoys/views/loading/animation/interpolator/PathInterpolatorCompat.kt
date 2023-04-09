package com.itoys.views.loading.animation.interpolator

import android.graphics.Path
import android.view.animation.Interpolator
import android.view.animation.PathInterpolator
import com.itoys.utils.SysUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc interpolator about path.
 */
class PathInterpolatorCompat {

    companion object {
        /**
         * Create an [Interpolator] for an arbitrary [Path]. The [Path]
         * must begin at {@code (0, 0)} and end at {@code (1, 1)}. The x-coordinate along the
         * {@link Path} is the input value and the output is the y coordinate of the line at that
         * point. This means that the Path must conform to a function {@code y = f(x)}.
         * <p>
         * The [Path] must not have gaps in the x direction and must not
         * loop back on itself such that there can be two points sharing the same x coordinate.
         *
         * @param path the [Path] to use to make the line representing the [Interpolator]
         * @return the [Interpolator] representing the [Path]
         */
        fun create(path: Path): Interpolator {
            if (SysUtils.isAndroid5()) {
                return PathInterpolatorCompatApi21.create(path)
            }

            return PathInterpolatorCompatBase.create(path)
        }

        /**
         * Create an [Interpolator] for a quadratic Bezier curve. The end points
         * {@code (0, 0)} and {@code (1, 1)} are assumed.
         *
         *  @param [controlX] the x coordinate of the quadratic Bezier control point
         *  @param [controlY] the y coordinate of the quadratic Bezier control point
         *  @return the [Interpolator] representing the quadratic Bezier curve
         */
        fun create(controlX: Float, controlY: Float): Interpolator {
            if (SysUtils.isAndroid5()) {
                return PathInterpolatorCompatApi21.create(controlX, controlY)
            }

            return PathInterpolatorCompatBase.create(controlX, controlY)
        }

        /**
         * Create an [Interpolator] for a cubic Bezier curve.  The end points
         * {@code (0, 0)} and {@code (1, 1)} are assumed.
         *
         * @param [controlX1] the x coordinate of the first control point of the cubic Bezier
         * @param [controlY1] the y coordinate of the first control point of the cubic Bezier
         * @param [controlX2] the x coordinate of the second control point of the cubic Bezier
         * @param [controlY2] the y coordinate of the second control point of the cubic Bezier
         * @return the [Interpolator] representing the cubic Bezier curve
         */
        fun create(
            controlX1: Float,
            controlY1: Float,
            controlX2: Float,
            controlY2: Float
        ): Interpolator {
            if (SysUtils.isAndroid5()) {
                return PathInterpolatorCompatApi21.create(controlX1, controlY1, controlX2, controlY2)
            }

            return PathInterpolatorCompatBase.create(controlX1, controlY1, controlX2, controlY2)
        }
    }

    private object PathInterpolatorCompatApi21 {
        fun create(path: Path): Interpolator {
            return PathInterpolator(path)
        }

        fun create(controlX: Float, controlY: Float): Interpolator {
            return PathInterpolator(controlX, controlY)
        }

        fun create(
            controlX1: Float,
            controlY1: Float,
            controlX2: Float,
            controlY2: Float
        ): Interpolator {
            return PathInterpolator(controlX1, controlY1, controlX2, controlY2)
        }
    }

    object PathInterpolatorCompatBase {
        fun create(path: Path): Interpolator {
            return PathInterpolatorDonut(path)
        }

        fun create(controlX: Float, controlY: Float): Interpolator {
            return PathInterpolatorDonut(controlX, controlY)
        }

        fun create(
            controlX1: Float,
            controlY1: Float,
            controlX2: Float,
            controlY2: Float
        ): Interpolator {
            return PathInterpolatorDonut(controlX1, controlY1, controlX2, controlY2)
        }
    }
}