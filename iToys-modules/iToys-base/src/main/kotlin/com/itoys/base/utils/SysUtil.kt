package com.itoys.base.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Process

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc
 */
object SysUtil {

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