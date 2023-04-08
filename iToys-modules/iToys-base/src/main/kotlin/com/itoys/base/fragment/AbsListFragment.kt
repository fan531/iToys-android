package com.itoys.base.fragment

import android.os.Bundle
import com.drake.brv.BindingAdapter
import com.itoys.base.databinding.ItoysFragmentListBinding
import com.itoys.base.mvi.AbsListViewModel
import com.itoys.base.mvi.ListUIIntent

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/04/2023
 * @desc list fragment
 */
abstract class AbsListFragment<T> : AbsMviFragment<ItoysFragmentListBinding, AbsListViewModel<T>>() {

    /** 列表适配器 */
    open val mListAdapter: BindingAdapter by lazy { listAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding?.itoysRecyclerList?.adapter = mListAdapter
    }

    override fun addClickListen() {
        super.addClickListen()
        mBinding?.itoysRefreshPage?.apply {
            onRefresh {
                mViewModel?.sendUIIntent(ListUIIntent.Refresh)
            }

            onLoadMore {
                mViewModel?.sendUIIntent(ListUIIntent.LoadMore)
            }
        }
    }

    /**
     * 列表适配器
     */
    abstract fun listAdapter(): BindingAdapter

    override fun addObserver() {
        super.addObserver()

        mViewModel?.let { viewModel ->
            listStateObserver(viewModel)
        }
    }

    /**
     * 列表状态订阅
     */
    abstract fun listStateObserver(viewModel: AbsListViewModel<T>)
}