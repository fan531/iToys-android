package com.itoys.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.itoys.base.R
import com.itoys.expansion.then
import com.itoys.views.statelayout.StateLayout
import com.itoys.views.statelayout.addStateLayout

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/03/2023
 * @desc
 */
abstract class AbsActivity<VB : ViewBinding> : AppCompatActivity() {

    /** activity 本身. */
    protected open var self: AppCompatActivity? = null

    protected open var mBinding: VB? = null

    protected open var stateLayout: StateLayout? = null

    protected open val titleBarListener = object : OnTitleBarListener {
        override fun onLeftClick(titleBar: TitleBar?) {
            finish()
        }
    }

    /**
     * 状态栏重试
     */
    protected open val stateLayoutRetry: () -> Unit = {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        self = this
        mBinding = createViewBinding()
        setContentView(mBinding?.root)

        supportActionBar?.hide()
        loadStateLayout()
        initView(savedInstanceState)
        // 设置 activity title
        findViewById<TitleBar>(R.id.title_bar)?.title = activityTitle()
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
     * 当前页面标题
     */
    protected open fun activityTitle(): String {
        return ""
    }

    /**
     * 数据初始化
     *
     * 默认activity [onResume] 之后会调用次方法
     * 如果不需要在[onResume]之后会调用, 需重写[onResume]方法
     */
    abstract fun initData()

    /**
     * 添加点击事件
     */
    protected open fun addClickListen() {
        // 空实现, 为 activity 点击事件
        findViewById<TitleBar>(R.id.title_bar)?.setOnTitleBarListener(titleBarListener)
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
        return R.color.theme_colorful_white
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
        return R.color.theme_colorful_black_percent15
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

    /**
     * 加载 state layout.
     */
    protected open fun loadStateLayout(view: View? = null) {
        if (useStateLayout()) {
            stateLayout = (view == null).then({
                addStateLayout(retry = stateLayoutRetry)
            }, {
                view?.addStateLayout(retry = stateLayoutRetry)
            })
        }
    }

    /**
     * 使用 state layout
     */
    protected open fun useStateLayout(): Boolean {
        return true
    }
}