package com.itoys.base.viewmodel

import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 07/03/2023
 * @desc view model 基类, 通用操作可放到这个类里.
 */
abstract class IToysViewModel : ViewModel(), DefaultLifecycleObserver {

    /**
     * 初始化
     * 会在activity initView 方法之后调用
     */
    open fun init(arguments: Bundle?) {
        // 空实现, 根据页面需求重写此方法
        // 一般会在此处理页面跳转传参数
    }

    /**
     * 在UI线程执行方法
     */
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}