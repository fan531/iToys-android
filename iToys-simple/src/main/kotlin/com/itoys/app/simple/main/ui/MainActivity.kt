package com.itoys.app.simple.main.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.itoys.app.simple.R
import com.itoys.app.simple.databinding.ItoysSimpleActivityMainBinding
import com.itoys.app.simple.main.mvi.MainUIIntent
import com.itoys.app.simple.main.mvi.MainViewModel
import com.itoys.app.simple.viewmodel.MainViewModelFactory
import com.itoys.base.activity.AbsMviActivity
import com.itoys.base.mvi.ToastUIState
import com.itoys.expansion.doOnClick
import com.itoys.views.snack.Prompt
import com.itoys.views.toast.ToastyStatus
import com.itoys.views.toast.toast

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
        mBinding?.simpleBtnToastyInfo?.doOnClick {
            showToast(
                R.string.simple_str_toasty_info,
                status = ToastyStatus.INFO
            )
        }
        mBinding?.simpleBtnToastyWarning?.doOnClick {
            showToast(
                R.string.simple_str_toasty_warning,
                status = ToastyStatus.WARING
            )
        }
        mBinding?.simpleBtnToastySuccess?.doOnClick {
            showToast(
                R.string.simple_str_toasty_success,
                status = ToastyStatus.SUCCESS
            )
        }
        mBinding?.simpleBtnToastyError?.doOnClick {
            showToast(
                R.string.simple_str_toasty_error,
                status = ToastyStatus.ERROR
            )
        }
        mBinding?.simpleBtnShowLoading?.doOnClick {
            showLoading(
                mBinding?.simpleBtnShowLoading, MainUIIntent.TestShowLoading(showLoading = false)
            )
        }

        mBinding?.simpleBtnLoadingSuccess?.doOnClick {
            showLoading(
                mBinding?.simpleBtnLoadingSuccess, MainUIIntent.TestStateLoading(isSuccess = true)
            )
        }

        mBinding?.simpleBtnLoadingError?.doOnClick {
            showLoading(
                mBinding?.simpleBtnLoadingSuccess, MainUIIntent.TestStateLoading(isSuccess = false)
            )
        }

        mBinding?.simpleBtnSnack?.doOnClick {
            mViewModel.sendToastUIState(
                ToastUIState.TopSnack(
                    getString(R.string.simple_str_show_snack),
                    prompt = Prompt.SUCCESS
                )
            )
        }
    }

    private fun showToast(@StringRes messageId: Int, status: ToastyStatus? = null) {
        toast(messageId, status = status)
    }

    private fun showLoading(view: View?, postIntent: MainUIIntent) {
        mViewModel.sendUIIntent(MainUIIntent.TestShowLoading(showLoading = true))

        view?.postDelayed(
            { mViewModel.sendUIIntent(postIntent) }, 2000
        )
    }

    override fun activityTitle(): String {
        return getString(R.string.simple_str_main_title)
    }
}