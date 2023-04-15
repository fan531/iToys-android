package com.itoys.app.splash.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.itoys.app.databinding.AppActivitySplashBinding
import com.itoys.app.simple.main.ui.MainActivity
import com.itoys.app.splash.mvi.IToysSplashViewModel
import com.itoys.app.viewmodel.IToysAppViewModelFactory
import com.itoys.base.activity.AbsMviActivity
import com.itoys.expansion.actOpen

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 05/03/2023
 * @desc
 */
class IToysSplashActivity : AbsMviActivity<AppActivitySplashBinding, IToysSplashViewModel>() {

    override val mViewModel by viewModels<IToysSplashViewModel> { IToysAppViewModelFactory }

    override fun createViewBinding(): AppActivitySplashBinding {
        return AppActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        self?.apply {
            actOpen(MainActivity::class)
            finish()
        }
    }
}