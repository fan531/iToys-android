package com.itoys.kit.core

import com.itoys.utils.ScreenUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 30/03/2023
 * @desc 保存上一次IToys kit View的位置信息
 */
class IToysKitViewPositionInfo {

    private var isPortrait = true

    private var kitViewWidth = 0
    private var kitViewHeight = 0

    private var startMarginPercent = 0f
    private var topMarginPercent = 0f

    var iToysKitViewWidth: Int
        get() = kitViewWidth
        set(value) {
            kitViewWidth = value
        }

    var iToysKitViewHeight: Int
        get() = kitViewHeight
        set(value) {
            kitViewHeight = value
        }

    fun setPortrait() {
        this.isPortrait = ScreenUtils.isPortrait()
    }

    fun isPortrait(): Boolean = this.isPortrait

    fun setStartMargin(startMargin: Int) {
        this.startMarginPercent = startMargin.toFloat() / ScreenUtils.getScreenWidth().toFloat()
    }

    fun getStartMarginPercent(): Float = this.startMarginPercent

    fun setTopMargin(topMargin: Int) {
        this.topMarginPercent = topMargin.toFloat() / ScreenUtils.getScreenHeight().toFloat()
    }

    fun getTopMarginPercent(): Float = this.topMarginPercent
}