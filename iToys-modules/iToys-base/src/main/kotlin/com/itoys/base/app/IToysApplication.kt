package com.itoys.base.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.itoys.base.utils.SysUtil

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc application 基类.
 */
abstract class IToysApplication : Application() {

    abstract val appInit: IToysAppInit

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 如果项目方法数超过65536, 则需要使用MultiDex进行分包
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()

        if (SysUtil.isMainProcess(this)) {
            appInit.syncInit(application = this)
        }

        appInit.asyncInit(application = this)
    }

    /**
     * 用户同意隐私协议后初始化
     */
    fun initCompliance() {
        appInit.initCompliance(this)
    }
}