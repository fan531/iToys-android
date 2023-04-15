package com.itoys.app.simple.state.mvi

import com.itoys.base.mvi.IUIIntent
import com.itoys.views.statelayout.PageStatus

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 15/04/2023
 * @desc
 */
sealed class StateLayoutIntent : IUIIntent {
    class StateIntent(val state: PageStatus) : StateLayoutIntent()
}