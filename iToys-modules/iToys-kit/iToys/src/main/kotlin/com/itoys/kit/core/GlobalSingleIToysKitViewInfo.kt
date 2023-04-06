package com.itoys.kit.core

import android.os.Bundle

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 01/04/2023
 * @desc 关于全局KitView的基本信息, 由于普通的浮标是每个页面自己管理的
 * 需要有一个map用来保存当前每个类型的kitView, 便于新开页面和页面resume时的kitView添加
 */
data class GlobalSingleIToysKitViewInfo(
    val kitViewClass: Class<out AbsIToysKitView?>,
    val tag: String,
    val mode: IToysKitViewLaunchMode,
    val bundle: Bundle?,
)