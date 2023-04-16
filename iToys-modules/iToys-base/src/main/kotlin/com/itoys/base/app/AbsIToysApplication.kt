package com.itoys.base.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.itoys.expansion.launchOnIO
import com.itoys.utils.SysUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc application 基类.
 */
abstract class AbsIToysApplication : Application() {

    lateinit var iToysApp: Application

    abstract val appInit: IToysAppInit

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 如果项目方法数超过65536, 则需要使用MultiDex进行分包
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        iToysApp = this

        if (SysUtils.isMainProcess(iToysApp)) {
            // 同步初始化, 继承AbsIToysApplication后可做自定义初始化
            appInit.syncInit(application = iToysApp)
        }

        launchOnIO {
            // 注册全局activity生命周期回调
            appInit.asyncInit(application = iToysApp)
        }
    }

    /**
     * 用户同意隐私协议后初始化
     */
    fun initCompliance() {
        appInit.initCompliance(iToysApp)
    }
}