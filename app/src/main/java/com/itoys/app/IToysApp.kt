package com.itoys.app

import android.app.Application
import com.itoys.base.app.IToysAppInit
import com.itoys.base.app.AbsIToysApplication

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 05/03/2023
 * @desc
 */
class IToysApp : AbsIToysApplication() {
    override val appInit: IToysAppInit
        get() = object : IToysAppInit {
            override fun syncInit(application: Application) {
            }

            override fun asyncInit(application: Application) {

            }

            override fun initCompliance(application: Application) {

            }
        }
}