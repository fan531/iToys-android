package com.itoys.base.fragment

import androidx.viewbinding.ViewBinding
import com.itoys.base.viewmodel.AbsIToysViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/03/2023
 * @desc
 */
abstract class IToysMvvmFragment<VB : ViewBinding, VM : AbsIToysViewModel> : IToysFragment<VB>() {
}