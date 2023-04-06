package com.itoys.kit.core

import android.view.MotionEvent
import android.view.View
import com.itoys.expansion.dp2px
import kotlin.math.abs

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 27/03/2023
 * @desc
 */
class TouchProxy(
    private val eventListener: OnTouchEventListener
) {

    private companion object {

        const val MIN_DISTANCE_MOVE = 4

        const val MIN_TAP_TIME = 1000

        val distance: Int by lazy {
            1.dp2px() * MIN_DISTANCE_MOVE
        }
    }

    private var mLastX: Int = 0
    private var mLastY: Int = 0
    private var mStartX: Int = 0
    private var mStartY: Int = 0

    private var mState: TouchState = TouchState.STATE_STOP

    private enum class TouchState {
        STATE_MOVE, STATE_STOP
    }

    fun onTouchEvent(v: View, event: MotionEvent): Boolean {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = x
                mStartY = y
                mLastY = y
                mLastX = x

                eventListener.onDown(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                val canContinue = when {
                    (abs(x - mStartX) < distance && abs(y - mStartY) < distance) -> {
                        mState != TouchState.STATE_STOP
                    }
                    mState != TouchState.STATE_MOVE -> {
                        mState = TouchState.STATE_MOVE
                        true
                    }
                    else -> true
                }

                if (canContinue) {
                    eventListener.onMove(mLastX, mLastY, x - mLastX, y - mLastY)
                    mLastX = x
                    mLastY = y
                    mState = TouchState.STATE_MOVE
                }
            }
            MotionEvent.ACTION_UP -> {
                eventListener.onUp(x, y)

                val isQuick = event.eventTime - event.downTime < MIN_TAP_TIME
                if (mState != TouchState.STATE_MOVE && isQuick) {
                    v.performClick()
                }

                mState = TouchState.STATE_STOP
            }
        }

        return true
    }

    interface OnTouchEventListener {

        fun onMove(x: Int, y: Int, dx: Int, dy: Int)

        fun onUp(x: Int, y: Int)

        fun onDown(x: Int, y: Int)
    }
}