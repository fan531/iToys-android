package com.itoys.base.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.itoys.base.activity.IToysActivityLifecycleCallbacks
import com.itoys.env.IToysEnv
import com.itoys.expansion.SysExpansion
import com.itoys.logcat.AndroidLogcatLogger
import com.itoys.logcat.LogPriority
import com.itoys.network.NetworkInitialization

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

        if (SysExpansion.isMainProcess(this)) {
            AndroidLogcatLogger.installOnDebuggableApp(this, LogPriority.VERBOSE)
            appInit.syncInit(application = this)
        }

        // 初始化网络服务
        NetworkInitialization.initialization()
        // 注册全局activity生命周期回调
        registerActivityLifecycleCallbacks(IToysActivityLifecycleCallbacks())
        appInit.asyncInit(application = this)
        // 集成 itoys-env, 已区分开发模式和发布模式, 开发模式可定义些功能
        IToysEnv.install(this)
    }

    /**
     * 用户同意隐私协议后初始化
     */
    fun initCompliance() {
        appInit.initCompliance(this)
    }
}