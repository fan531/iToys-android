package com.itoys.base.app

import android.app.Application

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc
 */
interface IToysAppInit {

    /**
     * 同步初始化.
     */
    fun syncInit(application: Application)

    /**
     * 异步初始化.
     */
    fun asyncInit(application: Application)

    /**
     * 合规初始化.
     */
    fun initCompliance(application: Application)
}