package com.itoys.app.simple.main.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.itoys.app.simple.R
import com.itoys.app.simple.databinding.ItoysSimpleActivityMainBinding
import com.itoys.app.simple.main.mvi.MainUIIntent
import com.itoys.app.simple.main.mvi.MainViewModel
import com.itoys.app.simple.viewmodel.MainViewModelFactory
import com.itoys.base.activity.AbsMviActivity
import com.itoys.expansion.doOnClick
import com.itoys.views.toast.errorToast
import com.itoys.views.toast.infoToast
import com.itoys.views.toast.normalToast
import com.itoys.views.toast.successToast
import com.itoys.views.toast.warningToast

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc
 */
class MainActivity : AbsMviActivity<ItoysSimpleActivityMainBinding, MainViewModel>() {

    override val mViewModel by viewModels<MainViewModel> { MainViewModelFactory }

    override fun createViewBinding(): ItoysSimpleActivityMainBinding {
        return ItoysSimpleActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding?.simpleTitleBar?.title = activityTitle()
    }

    override fun addClickListen() {
        super.addClickListen()
        mBinding?.simpleBtnToastyNormal?.doOnClick {
            mViewModel.sendUIIntent(MainUIIntent.TestToast)
        }
        mBinding?.simpleBtnToastyInfo?.doOnClick { infoToast(R.string.simple_str_toasty_info) }
        mBinding?.simpleBtnToastyWarning?.doOnClick { warningToast(R.string.simple_str_toasty_warning) }
        mBinding?.simpleBtnToastySuccess?.doOnClick { successToast(R.string.simple_str_toasty_success) }
        mBinding?.simpleBtnToastyError?.doOnClick { errorToast(R.string.simple_str_toasty_error) }
        mBinding?.simpleBtnShowLoading?.doOnClick {
            mViewModel.sendUIIntent(MainUIIntent.TestLoading(showLoading = true))

            mBinding?.simpleBtnShowLoading?.postDelayed({
                mViewModel.sendUIIntent(MainUIIntent.TestLoading(showLoading = false))
            }, 2000)
        }
    }

    override fun activityTitle(): String {
        return getString(R.string.simple_str_main_title)
    }
}