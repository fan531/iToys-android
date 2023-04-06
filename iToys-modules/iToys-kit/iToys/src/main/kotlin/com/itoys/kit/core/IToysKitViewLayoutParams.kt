package com.itoys.kit.core

import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 26/03/2023
 * @desc view 初始化位置
 */
class IToysKitViewLayoutParams {

    companion object {

        /**
         * 悬浮窗不能获取焦点
         */
        const val FLAG_NOT_FOCUSABLE = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        const val FLAG_NOT_TOUCHABLE = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE

        /**
         * 是否允许超出屏幕
         */
        const val FLAG_LAYOUT_NO_LIMITS = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

        /**
         * 悬浮窗不能获取焦点并且不相应触摸
         */
        const val FLAG_NOT_FOCUSABLE_AND_NOT_TOUCHABLE = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE

        const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    var gravity: Int = Gravity.START
    var x: Int = 0
    var y: Int = 0
    var width: Int = 0
    var height: Int = 0
}