package com.itoys.kit

import android.app.Application
import android.graphics.drawable.Drawable

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 25/03/2023
 * @desc itoys kit
 */
object IToysKit {

    class Builder(private val application: Application) {

        /**
         * 是否显示入口icon
         */
        fun setAlwaysShowEnterIcon(alwaysShow: Boolean): Builder = this.apply {
        }

        fun setCustomEnterIcon(iToysIconRes: Int): Builder = this.apply {
        }

        fun setCustomEnterIcon(iToysIconDrawable: Drawable?): Builder = this.apply {
        }

        fun build() {
            IToysKitReal.install(application)
        }
    }
}