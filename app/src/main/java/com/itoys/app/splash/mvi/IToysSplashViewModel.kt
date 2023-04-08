package com.itoys.app.splash.mvi

import com.itoys.base.mvi.AbsViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/03/2023
 * @desc
 */

class IToysSplashViewModel : AbsViewModel<SplashUIState, SplashUIIntent>() {
    override fun createUIState(): SplashUIState {
        return SplashUIState()
    }

    override fun handlerIntent(intent: SplashUIIntent) {
        // 空实现
    }
}