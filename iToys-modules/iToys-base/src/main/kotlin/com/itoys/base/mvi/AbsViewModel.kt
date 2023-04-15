package com.itoys.base.mvi

import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itoys.expansion.invalid
import com.itoys.expansion.launchOnIO
import com.itoys.logcat.logcat
import com.itoys.network.entity.ApiState
import com.itoys.network.entity.BaseEntity
import com.itoys.network.exception.ApiResultCode
import com.itoys.network.exception.ResultException
import com.itoys.network.http.handlerException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 07/03/2023
 * @desc view model 基类, 通用操作可放到这个类里.
 */
abstract class AbsViewModel<U : IUIState, I : IUIIntent> : ViewModel(),
    DefaultLifecycleObserver {

    /** view 状态 flow */
    private val _uiStateFlow: MutableStateFlow<U> by lazy { MutableStateFlow(createUIState()) }
    val uiStateFlow: StateFlow<U> = _uiStateFlow

    /** view 事件 */
    private val _uiIntentFlow: Channel<I> = Channel()

    /** loading 事件 */
    private val _loadingIntentFlow: Channel<LoadingUIState> = Channel()
    val loadingUiStateFlow: Flow<LoadingUIState?> = _loadingIntentFlow.receiveAsFlow()

    /** toast 状态 flow */
    private val _toastUiStateFlow: Channel<ToastUIState> = Channel()
    val toastUiStateFlow: Flow<ToastUIState?> = _toastUiStateFlow.receiveAsFlow()

    /** state layout 状态 flow */
    private val _stateLayoutUiStateFlow: Channel<StateLayoutUIState> = Channel()
    val stateLayoutUiStateFlow: Flow<StateLayoutUIState?> = _stateLayoutUiStateFlow.receiveAsFlow()

    init {
        launchOnUI {
            _uiIntentFlow.receiveAsFlow().collect { handlerIntent(it) }
        }
    }

    /**
     * 创建 view 状态
     */
    protected abstract fun createUIState(): U

    /**
     * 消息分发事件
     */
    protected abstract fun handlerIntent(intent: I)

    /**
     * 发送 view 状态
     */
    fun sendUIState(block: U.() -> U) {
        _uiStateFlow.update { block(_uiStateFlow.value) }
    }

    /**
     * 发送 loading 状态
     */
    fun sendLoadingUIState(block: LoadingUIState) {
        launchOnUI { _loadingIntentFlow.send(block) }
    }

    /**
     * 发送 toast 状态
     */
    fun sendToastUIState(block: ToastUIState) {
        launchOnUI { _toastUiStateFlow.send(block) }
    }

    /**
     * 发送 state layout 状态
     */
    fun sendStateLayoutUIState(block: StateLayoutUIState) {
        launchOnUI { _stateLayoutUiStateFlow.send(block) }
    }

    /**
     * 发送 view 事件
     */
    fun sendUIIntent(uiIntent: I) {
        launchOnIO { _uiIntentFlow.send(uiIntent) }
    }

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
    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    /**
     * 发送请求
     *
     * [showLoading] 是否显示 loading, 默认显示
     * [showToast] 是否显示 toast, 默认显示
     * [showSnack] 是否显示 snack, 默认不显示
     * [requestTag] 请求tag
     * [success] 请求tag
     * [handleEx] 默认实现, 提示信息由[showToast], [showSnack] 来控制, 如果都不显示, 默认框架提示
     * [request] 请求
     */
    fun <T : Any> launchRequestWithFlow(
        showLoading: Boolean = true,
        showToast: Boolean = true,
        showTopSnack: Boolean = false,
        showBottomSnack: Boolean = false,
        requestTag: String = "",
        success: ((T?) -> Unit)? = null,
        handleEx: suspend (ResultException) -> Unit = { ex ->
            val msg = ex.msg.invalid("请求出现异常")
            when {
                showToast -> sendToastUIState(ToastUIState.Toast(msg))
                showTopSnack -> sendToastUIState(ToastUIState.TopSnack(msg))
                showBottomSnack -> sendToastUIState(ToastUIState.BottomSnack(msg))
                else -> sendLoadingUIState(LoadingUIState.State(isSuccess = true, message = msg))
            }
        },
        request: suspend () -> BaseEntity<T>,
    ) {
        launchOnUI {
            sendLoadingUIState(LoadingUIState.Loading(showLoading))

            flow {
                val baseEntity: BaseEntity<T> = request()
                when (baseEntity.state) {
                    ApiState.Success -> emit(baseEntity.data)
                    ApiState.Error -> throw ResultException(baseEntity.errorCode, baseEntity.msg)
                }
            }.flowOn(Dispatchers.IO)
                .onStart { logcat { "$requestTag is start." } }
                .onEmpty {
                    logcat { "$requestTag is empty." }
                    throw ResultException(ApiResultCode.UNKNOWN, "请求出现异常")
                }
                .onCompletion {
                    logcat { "$requestTag is completion." }
                    if (showLoading) {
                        sendLoadingUIState(LoadingUIState.Loading(showLoading = false))
                    }
                }
                .catch {  exception ->
                    handleEx.invoke(exception.handlerException())
                }
                .collect { data ->
                    success?.invoke(data)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}