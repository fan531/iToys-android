package com.itoys.base.activity

import com.itoys.base.databinding.ItoysActivityListWithinTitleBinding
import com.itoys.base.mvi.AbsIToysListViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/03/2023
 * @desc
 */
abstract class AbsIToysListActivity<T> :
    AbsIToysMvvmActivity<ItoysActivityListWithinTitleBinding, AbsIToysListViewModel<T>>() {

    override fun createViewBinding(): ItoysActivityListWithinTitleBinding {
        return ItoysActivityListWithinTitleBinding.inflate(layoutInflater)
    }

    override fun addObserver() {
        super.addObserver()

        mViewModel?.let { viewModel ->
            listStateObserver(viewModel)
        }
    }

    /**
     * 列表状态订阅
     */
    abstract fun listStateObserver(viewModel: AbsIToysListViewModel<T>)
}