package com.itoys.app.simple.main.mvi

import com.itoys.base.mvi.IUIIntent

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc simple main view intent.
 */
sealed class MainUIIntent : IUIIntent {

    object TestToast : MainUIIntent()
}