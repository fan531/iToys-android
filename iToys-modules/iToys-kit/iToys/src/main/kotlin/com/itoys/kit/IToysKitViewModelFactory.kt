package com.itoys.kit

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itoys.kit.tool.viewmodel.ToolPanelViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 04/04/2023
 * @desc kit module view model 工厂
 */
val IToysKitViewModelFactory: ViewModelProvider.Factory = viewModelFactory {

    /** 工具面板 view model */
    initializer {
        ToolPanelViewModel()
    }
}