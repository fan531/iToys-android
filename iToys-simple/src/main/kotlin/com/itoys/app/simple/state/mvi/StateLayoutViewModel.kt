package com.itoys.app.simple.state.mvi

import com.itoys.base.mvi.AbsViewModel
import com.itoys.base.mvi.StateLayoutUIState
import com.itoys.views.statelayout.PageStatus

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 15/04/2023
 * @desc 状态布局
 */
class StateLayoutViewModel : AbsViewModel<StateLayoutState, StateLayoutIntent>() {

    override fun createUIState(): StateLayoutState {
        return StateLayoutState()
    }

    override fun handlerIntent(intent: StateLayoutIntent) {
        when (intent) {
            is StateLayoutIntent.StateIntent -> {
                testState(intent.state)
            }
        }
    }

    private fun testState(state: PageStatus) {
        sendStateLayoutUIState(StateLayoutUIState(state))
    }
}