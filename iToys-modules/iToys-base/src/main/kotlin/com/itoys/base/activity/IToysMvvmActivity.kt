package com.itoys.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.itoys.base.viewmodel.IToysViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc
 */
abstract class IToysMvvmActivity<VB : ViewBinding, VM: IToysViewModel> : IToysActivity<VB>() {

    /** view model */
    abstract val mViewModel: VM?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel?.init(savedInstanceState)
        addObserver()
    }

    override fun initView(savedInstanceState: Bundle?) {
        // 初始化页面
    }

    override fun initData() {
        // 加载页面数据
    }

    /**
     * 添加观察者, 注册监听
     */
    protected open fun addObserver() {
        // 添加观察者, 注册监听
        mViewModel?.let { lifecycle.addObserver(it) }
    }
}