package com.itoys.base.mvi

import com.itoys.views.snack.Prompt
import com.itoys.views.statelayout.PageStatus
import com.itoys.views.toast.ToastyStatus

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/04/2023
 * @desc mvi 模式 interface
 */

/**
 * view UI 状态
 */
interface IUIState

/**
 * view 事件
 */
interface IUIIntent

/**
 * loading UI 状态
 */
sealed class LoadingUIState {

    data class Loading(var showLoading: Boolean) : LoadingUIState()

    data class State(val isSuccess: Boolean, val message: String) : LoadingUIState()
}

/**
 * 状态 layout UI 状态
 */
class StateLayoutUIState(val status: PageStatus)

/**
 * toast UI 状态
 */
sealed class ToastUIState {
    data class Toast(
        val message: String,
        val status: ToastyStatus? = null
    ) : ToastUIState()

    data class TopSnack(
        val message: String,
        val withLoading: Boolean = false,
        val prompt: Prompt? = null
    ) : ToastUIState()

    data class BottomSnack(val message: String, val prompt: Prompt? = null) : ToastUIState()
}

/**
 * list UI 状态
 */
data class IListUIState<out T>(
    val listUiState: ListUIState<T>
) : IUIState

sealed class ListUIState<out T> {

    class EMPTY<out T> : ListUIState<T>()

    data class RefreshList<out T>(val list: List<T>?) : ListUIState<T>()

    data class LoadMoreList<out T>(val list: List<T>?) : ListUIState<T>()
}

/**
 * list 事件
 */
sealed class ListUIIntent : IUIIntent {

    object Refresh : ListUIIntent()

    object LoadMore : ListUIIntent()
}