package com.itoys.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.itoys.expansion.then
import com.itoys.views.statelayout.StateLayout
import com.itoys.views.statelayout.addStateLayout

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/03/2023
 * @desc
 */
abstract class AbsFragment<VB : ViewBinding> : Fragment() {

    /** 是否已经加载过 */
    private var isLoaded: Boolean = false

    protected open var mBinding: VB? = null

    protected open var stateLayout: StateLayout? = null

    /**
     * 状态栏重试
     */
    protected open val stateLayoutRetry: () -> Unit = {

    }

    /**
     * 创建ViewBinding.
     */
    abstract fun createViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadStateLayout()
        initialize(savedInstanceState)
        addClickListen()
    }

    override fun onResume() {
        super.onResume()
        if (needLazyLoad() && !isLoaded) {
            lazyInit()
            isLoaded = true
        }
    }

    /**
     * 添加点击事件
     */
    protected open fun addClickListen() {
        // 空实现, 为 activity 点击事件
    }

    /**
     * 初始化
     */
    abstract fun initialize(savedInstanceState: Bundle?)

    /**
     * 懒加载初始化
     */
    abstract fun lazyInit()

    /**
     * 默认需要懒加载
     */
    protected open fun needLazyLoad(): Boolean {
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

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}