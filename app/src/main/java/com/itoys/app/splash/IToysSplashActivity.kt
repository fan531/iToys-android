package com.itoys.app.splash

import androidx.activity.viewModels
import com.itoys.app.databinding.AppActivitySplashBinding
import com.itoys.app.viewmodel.IToysAppViewModelFactory
import com.itoys.base.activity.AbsIToysMvvmActivity

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 05/03/2023
 * @desc
 */
class IToysSplashActivity : AbsIToysMvvmActivity<AppActivitySplashBinding, IToysSplashViewModel>() {

    override val mViewModel by viewModels<IToysSplashViewModel> { IToysAppViewModelFactory }

    override fun createViewBinding(): AppActivitySplashBinding {
        return AppActivitySplashBinding.inflate(layoutInflater)
    }
}