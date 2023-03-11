package com.itoys.expansion

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Process

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc
 */
object SysExpansion {
    /**
     * 是否是 Android 4.1 及以上版本
     */
    @JvmStatic
    fun isAndroidJelly(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    /**
     * 是否是 Android 4.4 及以上版本
     */
    @JvmStatic
    fun isAndroid4(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    /**
     * 是否是 Android 5.0 及以上版本
     */
    @JvmStatic
    fun isAndroid5(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 是否是 Android 5.1 及以上版本
     */
    @JvmStatic
    fun isAndroidLollipopMr1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
    }

    /**
     * 是否是 Android 6.0 及以上版本
     */
    @JvmStatic
    fun isAndroid6(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /**
     * 是否是 Android 7.0 及以上版本
     */
    @JvmStatic
    fun isAndroid7(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    /**
     * 是否是 Android 8.0 及以上版本
     */
    @JvmStatic
    fun isAndroid8(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    /**
     * 是否是 Android 9.0 及以上版本
     */
    @JvmStatic
    fun isAndroid9(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    /**
     * 是否是 Android 10.0 及以上版本
     */
    @JvmStatic
    fun isAndroid10(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    /**
     * 是否是 Android 11.0 及以上版本
     */
    @JvmStatic
    fun isAndroid11(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
    }

    /**
     * 是否是 Android 12 及以上版本
     */
    @JvmStatic
    fun isAndroid12(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    /**
     * 是否是 Android 12 及以上版本
     */
    @JvmStatic
    fun isAndroid13(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    /**
     * 判断当前进程是否是主进程
     */
    @JvmStatic
    fun isMainProcess(context: Context): Boolean {
        val packageName = context.packageName
        val processName = getProcessName(context, Process.myPid())
        return packageName == processName
    }

    /**
     * 根据进程 ID 获取进程名.
     */
    @JvmStatic
    fun getProcessName(context: Context, pid: Int): String {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfoList = am.runningAppProcesses ?: return ""
        for (processInfo in processInfoList) {
            if (processInfo.pid == pid) {
                return processInfo.processName
            }
        }
        return ""
    }
}