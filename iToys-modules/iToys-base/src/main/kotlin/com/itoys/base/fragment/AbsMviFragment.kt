package com.itoys.base.fragment

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
 * @date 08/04/2023
 * @desc
 */
abstract class AbsMviFragment<VB : ViewBinding,
        VM : AbsViewModel<out IUIState, out IUIIntent>> : AbsFragment<VB>() {

    /** view model */
    abstract val mViewModel: VM?

    override fun initialize(savedInstanceState: Bundle?) {
        mViewModel?.init(arguments)
        addObserver()
        initView(savedInstanceState)
    }

    override fun lazyInit() {
        initData()
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 数据初始化
     *
     * 默认activity [onResume] 之后会调用次方法
     * 如果不需要在[onResume]之后会调用, 需重写[onResume]方法
     */
    abstract fun initData()

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