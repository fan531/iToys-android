package com.itoys.base.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.itoys.base.mvi.AbsViewModel
import com.itoys.base.mvi.IUIIntent
import com.itoys.base.mvi.IUIState
import com.itoys.base.mvi.LoadingUIState
import com.itoys.base.mvi.ToastUIState

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc
 */
abstract class AbsMviActivity<VB : ViewBinding,
        VM : AbsViewModel<out IUIState, out IUIIntent>> : AbsActivity<VB>() {

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
        mViewModel?.let { viewModel ->
            lifecycle.addObserver(viewModel)

            loadingStateObserver(viewModel)
            toastStateObserver(viewModel)
        }
    }

    /**
     * loading 状态订阅
     */
    private fun loadingStateObserver(viewModel: VM) {
        lifecycleScope.launchWhenStarted {
            viewModel.loadingUiStateFlow.collect { state ->
                when (state) {
                    is LoadingUIState.Loading -> {
                        if (state.showLoading) {
                            // 显示 loading
                        } else {
                            // 隐藏 loading
                        }
                    }

                    is LoadingUIState.Error -> {
                        // 显示 错误 loading

                    }

                    else -> {}
                }
            }
        }
    }

    /**
     * toast 状态订阅
     */
    private fun toastStateObserver(viewModel: VM) {
        lifecycleScope.launchWhenStarted {
            viewModel.toastUiStateFlow.collect { state ->
                when (state) {
                    is ToastUIState.Toast -> {
                       // toast message
                    }

                    is ToastUIState.Snack -> {
                        // snack message

                    }

                    else -> {}
                }
            }
        }
    }
}