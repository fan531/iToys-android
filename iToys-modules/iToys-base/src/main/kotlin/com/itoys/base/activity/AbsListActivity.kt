package com.itoys.base.activity

import android.os.Bundle
import com.drake.brv.BindingAdapter
import com.itoys.base.databinding.ItoysActivityListWithinTitleBinding
import com.itoys.base.mvi.AbsListViewModel
import com.itoys.base.mvi.ListUIIntent

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/03/2023
 * @desc list activity.
 */
abstract class AbsListActivity<T> :
    AbsMviActivity<ItoysActivityListWithinTitleBinding, AbsListViewModel<T>>() {

    /** 列表适配器 */
    open val mListAdapter: BindingAdapter by lazy { listAdapter() }

    override fun createViewBinding(): ItoysActivityListWithinTitleBinding {
        return ItoysActivityListWithinTitleBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding?.titleBar?.title = listTitle()
        mBinding?.itoysRecyclerList?.adapter = mListAdapter
    }

    override fun addClickListen() {
        super.addClickListen()
        mBinding?.itoysRefreshPage?.onRefresh {
            mViewModel?.sendUIIntent(ListUIIntent.Refresh)
        }

        mBinding?.itoysRefreshPage?.onLoadMore {
            mViewModel?.sendUIIntent(ListUIIntent.LoadMore)
        }
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
    abstract fun listStateObserver(viewModel: AbsListViewModel<T>)

    /**
     * 列表标题
     */
    abstract fun listTitle(): String

    /**
     * 列表适配器
     */
    abstract fun listAdapter(): BindingAdapter
}