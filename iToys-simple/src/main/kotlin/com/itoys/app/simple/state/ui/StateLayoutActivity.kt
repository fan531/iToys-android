package com.itoys.app.simple.state.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.itoys.app.simple.R
import com.itoys.app.simple.databinding.ItoysSimpleActivityStateLayoutBinding
import com.itoys.app.simple.state.mvi.StateLayoutIntent
import com.itoys.app.simple.state.mvi.StateLayoutViewModel
import com.itoys.app.simple.viewmodel.MainViewModelFactory
import com.itoys.base.activity.AbsMviActivity
import com.itoys.expansion.doOnClick
import com.itoys.views.statelayout.PageStatus

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 15/04/2023
 * @desc
 */
class StateLayoutActivity :
    AbsMviActivity<ItoysSimpleActivityStateLayoutBinding, StateLayoutViewModel>() {

    override val mViewModel by viewModels<StateLayoutViewModel> { MainViewModelFactory }

    override val stateLayoutRetry: () -> Unit
        get() = {
            postStatus(mBinding?.clRoot, PageStatus.CONTENT)
        }

    override fun createViewBinding(): ItoysSimpleActivityStateLayoutBinding {
        return ItoysSimpleActivityStateLayoutBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadStateLayout(view = mBinding?.clRoot)
    }

    override fun addClickListen() {
        super.addClickListen()
        mBinding?.simpleBtnStateEmpty?.doOnClick {
            postStatus(mBinding?.simpleBtnStateEmpty, PageStatus.EMPTY)
        }

        mBinding?.simpleBtnStateError?.doOnClick {
            postStatus(mBinding?.simpleBtnStateEmpty, PageStatus.ERROR)
        }
    }

    private fun postStatus(view: View?, status: PageStatus) {
        mViewModel.sendUIIntent(StateLayoutIntent.StateIntent(PageStatus.LOADING))

        view?.postDelayed({
            mViewModel.sendUIIntent(StateLayoutIntent.StateIntent(status))
        }, 2500)
    }

    override fun activityTitle(): String {
        return getString(R.string.simple_str_main_state_layout)
    }
}