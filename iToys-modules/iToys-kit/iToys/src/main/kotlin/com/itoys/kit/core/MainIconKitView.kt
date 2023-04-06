package com.itoys.kit.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.itoys.expansion.doOnClick
import com.itoys.kit.IToysKit
import com.itoys.kit.R
import com.itoys.kit.repository.IToysKitRepository

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc 主悬浮按钮
 */
class MainIconKitView : AbsIToysKitView() {

    override fun onCreate(context: Context) { /*空实现*/
    }

    override fun onCreateView(context: Context, rootView: FrameLayout?): View {
        return LayoutInflater.from(context).inflate(R.layout.kit_main_launch_icon, rootView, false)
    }

    override fun onViewCreated(rootView: FrameLayout) {
        kitView?.id = R.id.id_main_launch
        kitView?.doOnClick {
            IToysKit.showToolPanel()
        }
    }

    override fun initIToysViewLayoutParams(params: IToysKitViewLayoutParams) {
        params.x = IToysKitRepository.kitViewLastPositionX
        params.y = IToysKitRepository.kitViewLastPositionY
        params.width = IToysKitViewLayoutParams.WRAP_CONTENT
        params.height = IToysKitViewLayoutParams.WRAP_CONTENT
    }
}