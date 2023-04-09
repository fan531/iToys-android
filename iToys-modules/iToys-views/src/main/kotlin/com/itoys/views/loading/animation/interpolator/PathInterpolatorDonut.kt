package com.itoys.views.loading.animation.interpolator

import android.graphics.Path
import android.graphics.PathMeasure
import android.view.animation.Interpolator

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc A path interpolator implementation compatible with API 4+.
 */
class PathInterpolatorDonut : Interpolator {

    companion object {
        const val PRECISION = 0.002f

        fun createQuad(controlX: Float, controlY: Float): Path {
            val path = Path()
            path.moveTo(0f, 0f)
            path.quadTo(controlX, controlY, 1f, 1f)
            return path
        }

        fun createQuad(
            controlX1: Float,
            controlY1: Float,
            controlX2: Float,
            controlY2: Float
        ): Path {
            val path = Path()
            path.moveTo(0f, 0f)
            path.cubicTo(controlX1, controlY1, controlX2, controlY2, 1f, 1f)
            return path
        }
    }

    private var mX: FloatArray
    private var mY: FloatArray

    constructor(path: Path) : super() {
        val pathMeasure = PathMeasure(path, false)
        val pathLength = pathMeasure.length
        val numPoints = (pathLength / PRECISION).toInt() + 1
        mX = FloatArray(numPoints) { 0f }
        mY = FloatArray(numPoints) { 0f }
        var distance: Float
        val position = FloatArray(2) { 0f }
        for (i in 0 until numPoints) {
            distance = (i * pathLength) / (numPoints - 1)
            pathMeasure.getPosTan(distance, position, null)

            mX[i] = position[0]
            mY[i] = position[1]
        }
    }

    constructor(controlX: Float, controlY: Float) : this(createQuad(controlX, controlY))
    constructor(controlX1: Float, controlY1: Float, controlX2: Float, controlY2: Float) : this(
        createQuad(controlX1, controlY1, controlX2, controlY2)
    )

    override fun getInterpolation(input: Float): Float {
        if (input < 0f) return 0f
        if (input > 1f) return 1f

        var startIndex = 0
        var endIndex = mX.size - 1
        var midIndex: Int
        while (endIndex - startIndex > 1) {
            midIndex = (startIndex + endIndex) / 2
            if (input < mX[midIndex]) {
                endIndex = midIndex;
            } else {
                startIndex = midIndex;
            }
        }

        val xRange = mX[endIndex] - mX[startIndex]
        if (xRange == 0f) {
            return mY[startIndex]
        }

        val tInRange = input - mX[startIndex]
        val fraction = tInRange / xRange
        val startY = mY[startIndex]
        val endY = mY[endIndex]

        return startY + (fraction * (endY - startY))
    }
}