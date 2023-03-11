package com.itoys.base.activity

import com.itoys.base.databinding.ItoysActivityListWithinTitleBinding
import com.itoys.base.viewmodel.IToysListViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/03/2023
 * @desc
 */
abstract class IToysListActivity<VM : IToysListViewModel>
    : IToysMvvmActivity<ItoysActivityListWithinTitleBinding, VM>() {

    override fun createViewBinding(): ItoysActivityListWithinTitleBinding {
        return ItoysActivityListWithinTitleBinding.inflate(layoutInflater)
    }
}