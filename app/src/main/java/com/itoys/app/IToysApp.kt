package com.itoys.app

import android.app.Application
import com.itoys.base.app.IToysAppInit
import com.itoys.base.app.AbsIToysApplication
import com.itoys.kit.IToysKit
import com.itoys.network.NetworkInitialization
import com.itoys.views.statelayout.StateConfig
import com.itoys.views.statelayout.handler.FadeStateChangedHandler

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 05/03/2023
 * @desc
 */
class IToysApp : AbsIToysApplication() {

    override val appInit: IToysAppInit
        get() = object : IToysAppInit {
            override fun syncInit(application: Application) {
                // 初始化网络服务
                NetworkInitialization.initialization()
                // 集成 itoys-kit, 已区分开发模式和发布模式, 开发模式可定义些功能
                IToysKit.Builder(application = iToysApp).build()
            }

            override fun asyncInit(application: Application) {
                // 初始化State layout
                StateConfig.Builder()
                    .setDefaultId()
                    .setStateChangedHandler(FadeStateChangedHandler())
                    .build()
            }

            override fun initCompliance(application: Application) {

            }
        }
}