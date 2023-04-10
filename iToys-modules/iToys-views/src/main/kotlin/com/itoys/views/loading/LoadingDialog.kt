package com.itoys.views.loading

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.DrawableRes
import com.itoys.expansion.sp2px
import com.itoys.views.R
import com.itoys.views.databinding.ItoysViewsDialogLoadingBinding
import com.itoys.views.dialog.AbsDialog

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 10/04/2023
 * @desc dialog about loading.
 */
class LoadingDialog : AbsDialog<ItoysViewsDialogLoadingBinding>() {

    companion object {
        private const val DISMISS_DELAYED = 1500L

        fun newDialog(builder: Builder.() -> Unit): LoadingDialog {
            val build = Builder().apply(builder).build()
            val dialog = LoadingDialog()
            dialog.setBuilder(build)
            return dialog
        }
    }

    private lateinit var builder: Builder

    fun setBuilder(builder: Builder) {
        this.builder = builder
    }

    class Builder {
        fun build(): Builder {
            return this
        }
    }

    private val horizontalMargin by lazy { 86.sp2px() }

    override fun createViewBinding(): ItoysViewsDialogLoadingBinding {
        return ItoysViewsDialogLoadingBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        /** 空实现 */
    }

    fun dismissWithSuccess(message: String) {
        changeLoadingView(R.drawable.views_icon_loading_success, message)

        mBinding?.viewsIvLoading?.postDelayed(
            { dismiss() },
            DISMISS_DELAYED
        )
    }

    fun dismissWithError(message: String) {
        changeLoadingView(R.drawable.views_icon_loading_error, message)

        mBinding?.viewsIvLoading?.postDelayed(
            { dismiss() },
            DISMISS_DELAYED
        )
    }

    private fun changeLoadingView(@DrawableRes icon: Int, message: String) {
        mBinding?.viewsLoadingView?.visibility = View.INVISIBLE
        mBinding?.viewsIvLoading?.visibility = View.VISIBLE
        mBinding?.viewsTvLoadingText?.text = message
        mBinding?.viewsIvLoading?.setImageResource(icon)
    }

    override fun dialogHorizontalMargin(): Int = horizontalMargin

    override fun dialogGravity(): Int = Gravity.CENTER
}