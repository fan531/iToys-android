package com.itoys.app.simple.main.mvi

import com.itoys.base.mvi.AbsViewModel
import com.itoys.base.mvi.LoadingUIState
import com.itoys.base.mvi.ToastUIState

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc simple main model.
 */
class MainViewModel : AbsViewModel<MainUIState, MainUIIntent>() {
    override fun createUIState(): MainUIState {
        return MainUIState()
    }

    override fun handlerIntent(intent: MainUIIntent) {
        when (intent) {
            is MainUIIntent.TestToast -> {
                testToast()
            }

            is MainUIIntent.TestLoading -> {
                testLoading(intent.showLoading)
            }
        }
    }

    /**
     * main intent test toast.
     */
    private fun testToast() {
        sendToastUIState { ToastUIState.Toast("test toast") }
    }

    /**
     * main intent test loading.
     */
    private fun testLoading(showLoading: Boolean) {
        sendLoadingUIState { LoadingUIState.Loading(showLoading = showLoading) }
    }
}