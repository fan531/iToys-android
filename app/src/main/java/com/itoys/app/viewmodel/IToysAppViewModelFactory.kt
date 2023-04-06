package com.itoys.app.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itoys.app.splash.mvi.IToysSplashViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 11/03/2023
 * @desc app module view model 工厂
 */

val IToysAppViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
    /** 闪屏页 view model */
    initializer {
        IToysSplashViewModel()
    }
}