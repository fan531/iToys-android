package com.itoys.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc
 */
abstract class IToysActivity<VB : ViewBinding> : AppCompatActivity() {

    /** activity 本身. */
    open var self: AppCompatActivity? = null

    protected open var mBinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        self = this

        supportActionBar?.hide()
        initView(savedInstanceState)
        addClickListen()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    /**
     * 创建ViewBinding.
     */
    abstract fun createViewBinding(): VB

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 数据初始化
     *
     * activity onResume 之后会调用次方法
     */
    abstract fun initData()

    /**
     * 添加点击事件
     */
    protected open fun addClickListen() {
        // 空实现, 为 activity 点击事件
    }

    override fun onContentChanged() {
        super.onContentChanged()
        initImmersionBar()
    }

    /**
     * 沉浸式状态栏
     */
    protected open fun initImmersionBar() {
        if (enableImmersionBar()) {
            try {
                ImmersionBar.with(this)
                    .statusBarColor(immersionBarColor())
                    .fitsSystemWindows(fitsSystemWindows())
                    .statusBarDarkFont(statusBarDarkFont())
                    .navigationBarColor(navigationBarColor(), navigationAlpha())
                    .navigationBarDarkIcon(navigationBarDarkIcon())
                    .keyboardEnable(enableKeyboard())
                    .init()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 开启沉浸式状态栏
     */
    protected open fun enableImmersionBar(): Boolean {
        return true
    }

    /**
     * 浸式状态栏颜色
     */
    protected open fun immersionBarColor(): Int {
        return com.itoys.theme.R.color.color_transparent
    }

    /**
     * 解决状态栏和布局重叠问题
     */
    protected open fun fitsSystemWindows(): Boolean {
        return true
    }

    /**
     * 状态栏字体是深色
     */
    protected open fun statusBarDarkFont(): Boolean {
        return true
    }

    /**
     * 导航栏颜色
     */
    protected open fun navigationBarColor(): Int {
        return com.itoys.theme.R.color.color_toasty_frame
    }

    protected open fun navigationAlpha(): Float {
        return 0.4f
    }

    /**
     * 导航栏图标是深色，不写默认为亮色
     */
    protected open fun navigationBarDarkIcon(): Boolean {
        return false
    }

    /**
     * 解决软键盘与底部输入框冲突问题
     */
    protected open fun enableKeyboard(): Boolean {
        return false
    }

    /**
     * 侧滑finish activity
     */
    protected open fun useSwipeFinish(): Boolean {
        return true
    }
}