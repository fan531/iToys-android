package com.itoys.base.mvi

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/04/2023
 * @desc mvi 模式 interface
 */

/**
 * view 状态
 */
interface IUIState

/**
 * view 事件
 */
interface IUIIntent

/**
 * loading 状态
 */
sealed class LoadingUIState {

    data class Loading(var showLoading: Boolean) : LoadingUIState()

    data class Error(val errorMsg: String) : LoadingUIState()
}

/**
 * toast 状态
 */
sealed class ToastUIState {
    data class Toast(val toastMsg: String) : ToastUIState()

    data class Snack(val snackMsg: String) : ToastUIState()
}

data class IListUIState<out T>(
    val listUiState: ListUIState<T>
) : IUIState

sealed class ListUIState<out T> {

    class EMPTY<out T> : ListUIState<T>()

    data class RefreshList<out T>(val list: List<T>?) : ListUIState<T>()

    data class LoadMoreList<out T>(val list: List<T>?) : ListUIState<T>()
}

sealed class IListUIIntent : IUIIntent {

    object Refresh : IListUIIntent()

    object LoadMore : IListUIIntent()
}