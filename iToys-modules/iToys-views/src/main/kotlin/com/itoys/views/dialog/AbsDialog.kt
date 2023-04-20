package com.itoys.views.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.itoys.expansion.tagName
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 10/04/2023
 * @desc Dialog basic abstract.
 */
abstract class AbsDialog<VB : ViewBinding> : DialogFragment() {

    protected open var mBinding: VB? = null

    /**
     * 创建ViewBinding.
     */
    abstract fun createViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = createViewBinding()
        initDialog()
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    protected open fun initDialog() {
        dialog?.window?.apply {
            // 设置动画
            setWindowAnimations(dialogAnim())
            // 设置dialog padding
            decorView.setPadding(dialogHorizontalMargin(), 0, dialogHorizontalMargin(), 0)
            val params = attributes
            params.width = dialogWidth()
            params.height = dialogHeight()
            params.gravity = dialogGravity()
            attributes = params
            isCancelable = touchCancelable()
        }
    }

    /**
     * 对话框位置
     */
    protected open fun dialogGravity(): Int {
        return Gravity.BOTTOM
    }

    /**
     * 对话框宽
     */
    protected open fun dialogWidth(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    /**
     * 对话框高
     */
    protected open fun dialogHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     * 对话框水平外间距
     */
    protected open fun dialogHorizontalMargin(): Int {
        return 0
    }

    /**
     * 对话框样式
     */
    protected open fun dialogStyle(): Int {
        return R.style.IToysApp_Dialog
    }

    /**
     * 外部触摸是否关闭
     */
    protected open fun touchCancelable(): Boolean {
        return true
    }

    /**
     * 对话框动画
     */
    protected open fun dialogAnim(): Int {
        return 0
    }

    /**
     * 显示对话框
     */
    fun showDialog(fm: FragmentManager) {
        setStyle(STYLE_NORMAL, dialogStyle())
        show(fm, javaClass.tagName)
    }
}