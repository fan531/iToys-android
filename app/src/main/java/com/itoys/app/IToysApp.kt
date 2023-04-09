package com.itoys.app

import android.app.Application
import com.itoys.base.app.IToysAppInit
import com.itoys.base.app.AbsIToysApplication
import com.itoys.kit.IToysKit

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 05/03/2023
 * @desc
 */
class IToysApp : AbsIToysApplication() {

    override val appInit: IToysAppInit
        get() = object : IToysAppInit {
            override fun syncInit(application: Application) {
                // 集成 itoys-kit, 已区分开发模式和发布模式, 开发模式可定义些功能
                IToysKit.Builder(application = iToysApp).build()
            }

            override fun asyncInit(application: Application) {

            }

            override fun initCompliance(application: Application) {

            }
        }
}