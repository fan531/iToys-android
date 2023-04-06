package com.itoys.kit.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.itoys.expansion.tagName
import com.itoys.expansion.then
import com.itoys.kit.repository.IToysKitRepository
import com.itoys.utils.ActivityUtils
import com.itoys.utils.ScreenUtils
import java.lang.ref.WeakReference

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 26/03/2023
 * @desc 页面浮标抽象类, 悬浮窗都需要继承该抽象接口
 */
abstract class AbsIToysKitView : IToysKitView {

    private companion object {
        val MAIN_ICON_NAME: String get() = MainIconKitView::class.tagName
    }

    class ViewArgs {
        var mode: IToysKitViewLaunchMode = IToysKitViewLaunchMode.SINGLE_INSTANCE
        var edgePinned: Boolean = false
    }

    private val mTouchEventListener: TouchProxy.OnTouchEventListener = object : TouchProxy.OnTouchEventListener {
        override fun onMove(x: Int, y: Int, dx: Int, dy: Int) {
            if (!canDrag()) return
            mNormalLayoutParams?.apply {
                this.leftMargin += dx
                this.topMargin += dy
            }

            updateViewLayout(tag, isActivityBackResume = false)
        }

        override fun onUp(x: Int, y: Int) {
            if (!canDrag()) return

            if (!viewProps.edgePinned) {
                endMoveAndRecord()
                return
            }

            animatedMoveToEdge()
        }

        override fun onDown(x: Int, y: Int) {
            if (!canDrag()) return
        }
    }

    private val viewProps = ViewArgs()

    /**
     * 页面启动模式
     */
    var mode: IToysKitViewLaunchMode
        get() = viewProps.mode
        set(value) { viewProps.mode = value }

    private val mTouchProxy: TouchProxy = TouchProxy(mTouchEventListener)

    /**
     * 创建FrameLayout#LayoutParams 内置悬浮窗调用
     */
    var mNormalLayoutParams: FrameLayout.LayoutParams? = null
        private set

    var tag = javaClass.tagName
    var bundle: Bundle? = null

    private var mAttachActivity: WeakReference<Activity?>? = null

    val activity: Activity?
        get() = mAttachActivity?.get() ?: ActivityUtils.getTopActivity()

    fun setActivity(activity: Activity?) {
        mAttachActivity = WeakReference(activity)
    }

    private var mRootView: IToysKitFrameLayout? = null

    val kitView: View?
        get() = mRootView

    /**
     * rootView的直接子View 一般是用户的xml布局 被添加到mRootView中
     */
    private var mChildView: View? = null

    /**
     * 用来保存root view的LayoutParams
     */
    private lateinit var mKitViewLayoutParams: IToysKitViewLayoutParams

    /**
     * kit view 的位置信息
     */
    private val mKitViewPositionInfo: IToysKitViewPositionInfo by lazy {
        var positionInfo = IToysKitViewManager.INSTANCE.getKitViewPositionInfo(tag)

        if (positionInfo == null) {
            positionInfo = IToysKitViewPositionInfo()
            IToysKitViewManager.INSTANCE.saveKitViewPositionInfo(tag, positionInfo)
        }

        positionInfo
    }

    /**
     * 根布局的实际宽
     */
    private var mKitViewWidth: Int = 0

    /**
     * 根布局的实际高
     */
    private var mKitViewHeight: Int = 0

    private var mViewTreeObserver: ViewTreeObserver? = null

    private val mOnGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener =
        ViewTreeObserver.OnGlobalLayoutListener {
            mRootView?.let {
                mKitViewWidth = it.measuredWidth
                mKitViewHeight = it.measuredHeight
                mKitViewPositionInfo.iToysKitViewWidth =  mKitViewWidth
                mKitViewPositionInfo.iToysKitViewHeight =  mKitViewHeight
            }
        }

    /**
     * 创建 kit view
     */
    @SuppressLint("ClickableViewAccessibility")
    fun performCreate(context: Context) {
        onCreate(context)
        mRootView = IToysKitFrameLayout(context)
        addViewTreeObserverListener()
        mChildView = onCreateView(context, mRootView)
        mRootView?.addView(mChildView)
        mRootView?.title = this.javaClass.name
        mRootView?.setOnTouchListener { v, event -> mTouchProxy.onTouchEvent(v, event) }
        mRootView?.let { onViewCreated(it) }
        mKitViewLayoutParams = IToysKitViewLayoutParams()
        mNormalLayoutParams = FrameLayout.LayoutParams(
            IToysKitViewLayoutParams.WRAP_CONTENT,
            IToysKitViewLayoutParams.WRAP_CONTENT
        ).apply { gravity = Gravity.START or Gravity.TOP }
        mKitViewLayoutParams.gravity = Gravity.START or Gravity.TOP
        initIToysViewLayoutParams(mKitViewLayoutParams)
        mNormalLayoutParams?.let { onNormalLayoutParamsCreated() }
    }

    /**
     * 确定普通浮标的初始位置
     */
    private fun onNormalLayoutParamsCreated() {
        mNormalLayoutParams?.apply {
            width = mKitViewLayoutParams.width
            height = mKitViewLayoutParams.height
        }
        val kitViewInfo = IToysKitViewManager.INSTANCE.getKitViewPosition(tag)
        if (kitViewInfo != null) {
            when (kitViewInfo.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> {
                    mNormalLayoutParams?.apply {
                        leftMargin = kitViewInfo.portraitPoint.x
                        topMargin = kitViewInfo.portraitPoint.y
                    }
                }
                Configuration.ORIENTATION_LANDSCAPE -> {
                    mNormalLayoutParams?.apply {
                        leftMargin = kitViewInfo.landscapePoint.x
                        topMargin = kitViewInfo.landscapePoint.y
                    }
                }
            }
        } else {
            mNormalLayoutParams?.apply {
                leftMargin = mKitViewLayoutParams.x
                topMargin = mKitViewLayoutParams.y
            }
        }

        portraitOrLandscape()
    }

    /**
     * 销毁 kit view
     */
    fun performDestroy() {
        removeViewTreeObserverListener()
        mRootView = null
        mAttachActivity = null
        onDestroy()
    }

    /**
     * 用于普通模式下的横竖屏切换
     */
    private fun portraitOrLandscape() {
        // 横竖屏切换兼容
        val kitViewInfo = IToysKitViewManager.INSTANCE.getKitViewPosition(tag)
        mNormalLayoutParams?.apply {
            when {
                ScreenUtils.isPortrait() && mKitViewPositionInfo.isPortrait() -> {
                    leftMargin = kitViewInfo?.portraitPoint?.x ?: mKitViewLayoutParams.x
                    topMargin = kitViewInfo?.portraitPoint?.y ?: mKitViewLayoutParams.y
                }

                ScreenUtils.isPortrait() && !mKitViewPositionInfo.isPortrait() -> {
                    leftMargin = ((kitViewInfo?.landscapePoint?.x ?: mKitViewLayoutParams.x) * mKitViewPositionInfo.getStartMarginPercent()).toInt()
                    topMargin = ((kitViewInfo?.landscapePoint?.y ?: mKitViewLayoutParams.y) * mKitViewPositionInfo.getTopMarginPercent()).toInt()
                }

                !ScreenUtils.isPortrait() && mKitViewPositionInfo.isPortrait() -> {
                    leftMargin = ((kitViewInfo?.portraitPoint?.x ?: mKitViewLayoutParams.x) * mKitViewPositionInfo.getStartMarginPercent()).toInt()
                    topMargin = ((kitViewInfo?.portraitPoint?.y ?: mKitViewLayoutParams.y) * mKitViewPositionInfo.getTopMarginPercent()).toInt()
                }

                !ScreenUtils.isPortrait() && !mKitViewPositionInfo.isPortrait() -> {
                    leftMargin = kitViewInfo?.landscapePoint?.x ?: mKitViewLayoutParams.x
                    topMargin = kitViewInfo?.landscapePoint?.y ?: mKitViewLayoutParams.y
                }
            }
        }

        mKitViewPositionInfo.setPortrait()
        mNormalLayoutParams?.also {
            mKitViewPositionInfo.setStartMargin(it.leftMargin)
            mKitViewPositionInfo.setTopMargin(it.topMargin)
        }

        if (tag == MAIN_ICON_NAME) {
            mNormalLayoutParams?.also {
                IToysKitRepository.kitViewLastPositionX = it.leftMargin
                IToysKitRepository.kitViewLastPositionY = it.topMargin
            }
        }

        IToysKitViewManager.INSTANCE.saveKitViewPosition(
            tag,
            mNormalLayoutParams?.leftMargin ?: 0,
            mNormalLayoutParams?.topMargin ?: 0
        )
    }

    /**
     * 添加根布局的layout回调
     */
    private fun addViewTreeObserverListener() {
        if (mViewTreeObserver == null && mRootView != null) {
            mViewTreeObserver = mRootView?.viewTreeObserver
            mViewTreeObserver?.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
        }
    }

    /**
     * 移出根布局的layout回调
     */
    private fun removeViewTreeObserverListener() {
        mViewTreeObserver?.let {
            if (it.isAlive) it.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
        }
    }

    override fun onResume() {
        mRootView?.requestLayout()
    }

    override fun onPause() { /*空实现*/ }

    override fun onDestroy() {
        IToysKitViewManager.INSTANCE.removeKitViewPositionInfo(tag)
        mAttachActivity = null
    }

    /**
     * 默认实现为true
     */
    override fun canDrag(): Boolean = true

    /**
     * 搭配shouldDealBackKey使用 自定义处理完以后需要返回true
     * 默认模式的onBackPressed 拦截在NormalDokitViewManager#getDokitRootContentView中被处理
     * 系统模式下的onBackPressed 在当前类的performCreate 初始话DoKitView时被处理
     * 返回false 表示交由系统处理
     * 返回 true 表示当前的返回事件已由自己处理 并拦截了改返回事件
     */
    override fun onBackPressed(): Boolean = false

    /**
     * 默认不自己处理返回按键
     */
    override fun shouldDealBackKey(): Boolean = false

    override fun onEnterBackground() { /* 空实现, app 进入后台 */ }

    override fun onEnterForeground() { /* 空实现, app 进入前台 */ }

    /**
     * 获取屏幕短边的长度 不包含statusBar
     *
     * @return
     */
    private val screenShortSideLength: Int
        get() = if (ScreenUtils.isPortrait()) {
            ScreenUtils.getScreenWidth()
        } else {
            ScreenUtils.getScreenHeight()
        }

    /**
     * 获取屏幕长边的长度 不包含statusBar
     *
     * @return
     */
    private val screenLongSideLength: Int
        get() = if (ScreenUtils.isPortrait()) {
            ScreenUtils.getScreenHeight()
        } else {
            ScreenUtils.getScreenWidth()
        }


    /**
     * 更新view的位置
     *
     * [isActivityBackResume] 是否是从其他页面返回时更新的位置
     */
    open fun updateViewLayout(tag: String, isActivityBackResume: Boolean) {
        if (mRootView == null || mChildView == null || mNormalLayoutParams == null) return

        val leftMargin: Int
        val topMargin: Int

        if (tag != MAIN_ICON_NAME) {
            if (mKitViewWidth != 0) mNormalLayoutParams?.width = mKitViewWidth
            if (mKitViewHeight != 0) mNormalLayoutParams?.height = mKitViewHeight

            val kitViewInfo = IToysKitViewManager.INSTANCE.getKitViewPosition(tag)
            when (kitViewInfo?.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> {
                    leftMargin = kitViewInfo.portraitPoint.x
                    topMargin = kitViewInfo.portraitPoint.y
                }

                else -> {
                    leftMargin = kitViewInfo?.landscapePoint?.x ?: 0
                    topMargin = kitViewInfo?.landscapePoint?.y ?: 0
                }
            }
        } else {
            leftMargin = IToysKitRepository.kitViewLastPositionX
            topMargin = IToysKitRepository.kitViewLastPositionY
            mNormalLayoutParams?.width = IToysKitViewLayoutParams.WRAP_CONTENT
            mNormalLayoutParams?.height = IToysKitViewLayoutParams.WRAP_CONTENT
        }

        mNormalLayoutParams?.apply {
            if (isActivityBackResume) {
                this.leftMargin = leftMargin
                this.topMargin = topMargin
            } else {
                mKitViewPositionInfo.setPortrait()
                mKitViewPositionInfo.setStartMargin(this.leftMargin)
                mKitViewPositionInfo.setTopMargin(this.topMargin)
            }

            resetBorderline()
            mRootView?.layoutParams = this
        }
    }

    /**
     * 限制边界
     *
     * 调用的时候必须保证是在控件能获取到宽高的前提下
     */
    private fun resetBorderline() {
        mNormalLayoutParams?.also {
            val leftMargin: Int
            val topMargin: Int

            if (ScreenUtils.isPortrait()) {
                leftMargin = screenShortSideLength - mKitViewWidth
                topMargin = screenLongSideLength - mKitViewHeight
            } else {
                leftMargin = screenLongSideLength - mKitViewWidth
                topMargin = screenShortSideLength - mKitViewHeight
            }

            if (it.leftMargin >= leftMargin) it.leftMargin = leftMargin
            if (it.topMargin >= topMargin) it.topMargin = topMargin

            if (it.leftMargin < 0) it.leftMargin = 0
            if (it.topMargin < 0) it.topMargin = 0
        }
    }

    /**
     * 停止移动和记录
     */
    private fun endMoveAndRecord() {
        if (tag == MAIN_ICON_NAME) {
            mNormalLayoutParams?.also {
                IToysKitRepository.kitViewLastPositionX = it.leftMargin
                IToysKitRepository.kitViewLastPositionY = it.topMargin
            }
        }

        mNormalLayoutParams?.also { IToysKitViewManager.INSTANCE.saveKitViewPosition(tag, it.leftMargin, it.topMargin) }
    }

    private fun animatedMoveToEdge() {
        val viewSize = mRootView?.width ?: return
        val parent = (mRootView?.parent as? ViewGroup) ?: return
        mNormalLayoutParams?.also { attrs ->
            makeAnimator(attrs.leftMargin, viewSize, parent.width) {
                addUpdateListener {v ->
                    attrs.leftMargin = v.animatedValue as Int
                    updateViewLayout(tag, isActivityBackResume = false)
                }

                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        endMoveAndRecord()
                    }
                })
            }
        }
    }

    private inline fun makeAnimator(
        from: Int,
        size: Int,
        containerSize: Int,
        setup: ValueAnimator.() -> Unit
    ) {
        if (size <= 0 || containerSize <= 0) return
        ValueAnimator.ofInt(
            from,
            (from <= (containerSize - size) / 2).then({ 0 }, { containerSize - size })
        )
            .apply {
                duration = 150L
                setup()
            }.start()
    }
}