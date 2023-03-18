package com.itoys.app.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.itoys.app.databinding.AppActivitySplashBinding
import com.itoys.app.viewmodel.IToysAppViewModelFactory
import com.itoys.base.activity.IToysMvvmActivity
import com.itoys.logcat.logcat

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 05/03/2023
 * @desc
 */
class IToysSplashActivity : IToysMvvmActivity<AppActivitySplashBinding, IToysSplashViewModel>() {

    override val mViewModel by viewModels<IToysSplashViewModel> { IToysAppViewModelFactory }

    override fun createViewBinding(): AppActivitySplashBinding {
        return AppActivitySplashBinding.inflate(layoutInflater)
    }
}