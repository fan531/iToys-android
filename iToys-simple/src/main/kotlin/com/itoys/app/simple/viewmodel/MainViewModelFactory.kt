package com.itoys.app.simple.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itoys.app.simple.main.mvi.MainViewModel
import com.itoys.app.simple.state.mvi.StateLayoutViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc simple module view model factory.
 */

val MainViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
    /** simple 主页 view model */
    initializer {
        MainViewModel()
    }

    initializer {
        StateLayoutViewModel()
    }
}