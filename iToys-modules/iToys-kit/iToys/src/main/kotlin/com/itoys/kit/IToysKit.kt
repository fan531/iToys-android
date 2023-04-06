package com.itoys.kit

import android.app.Application
import android.graphics.drawable.Drawable

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 25/03/2023
 * @desc itoys kit
 */
object IToysKit {

    /**
     * 直接显示工具面板页面
     */
    @JvmStatic
    fun showToolPanel() {
        IToysKitReal.showToolPanel()
    }

    class Builder(private val application: Application) {

        /**
         * 是否显示入口icon
         */
        fun setAlwaysShowEnterIcon(alwaysShow: Boolean): Builder = this.apply {
            IToysKitReal.setAlwaysShowEnterIcon(alwaysShow)
        }

        fun setCustomEnterIcon(iToysIconRes: Int): Builder = setCustomEnterIcon(application.getDrawable(iToysIconRes))

        fun setCustomEnterIcon(iToysIconDrawable: Drawable?): Builder = this.apply {
            IToysKitReal.setCustomEnterIcon(iToysIconDrawable)
        }

        fun build() {
            IToysKitReal.install(application)
        }
    }
}