package com.itoys.views.statelayout

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.itoys.logcat.LogPriority
import com.itoys.logcat.logcat
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 15/04/2023
 * @desc 缺省页切换处理
 */
interface StateChangedHandler {

    companion object DEFAULT : StateChangedHandler

    /**
     * StateLayout添加缺省页
     * @param container StateLayout
     * @param stateView 将被添加缺省页视图对象
     * @param tag 显示状态传入的tag
     */
    fun onAdd(container: StateLayout, stateView: View, status: PageStatus, tag: String?) {
        if (container.currStatus == status) return

        if (container.indexOfChild(stateView) != -1) {
            stateView.visibility = View.VISIBLE
        } else {
            container.addView(stateView)
        }
    }

    /**
     * StateLayout删除缺省页, 此方法比[onAdd]先执行
     * @param container StateLayout
     * @param stateView 将被删除缺省页视图对象
     * @param tag 显示状态传入的tag
     */
    fun onRemove(container: StateLayout, stateView: View, status: PageStatus, tag: String?) {
        if (container.currStatus != status) {
            stateView.visibility = View.GONE
        }
    }
}