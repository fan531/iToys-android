package com.itoys.views.statelayout

import android.content.Context
import android.util.ArrayMap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.airbnb.lottie.LottieAnimationView
import com.itoys.expansion.doOnClick
import com.itoys.expansion.tagName
import com.itoys.expansion.then
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 14/04/2023
 * @desc 全局缺省页
 */
class StateLayout(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val statusContainer: ArrayMap<PageStatus, StateInfo> by lazy { ArrayMap() }

    var currStatus: PageStatus = PageStatus.CONTENT
    private var isShowRetry: Boolean = false

    /** 处理缺省页状态变更 */
    private var stateChangedHandler: StateChangedHandler = StateManager.stateChangedHandler

    /** 加载中动画 */
    private var loadingLottieView: LottieAnimationView? = null

    /** 是否成功加载过 */
    private var isLoaded = false

    /**
     * 重试ids
     */
    private var retryIds: IntArray? = null

    /**
     * 加载页面 layoutId
     */
    @LayoutRes
    private var loadingLayoutId: Int = View.NO_ID

    /**
     * 空页面 layoutId
     */
    @LayoutRes
    private var emptyLayoutId: Int = View.NO_ID

    /**
     * 加载错误页面 layoutId
     */
    @LayoutRes
    private var errorLayoutId: Int = View.NO_ID

    /**
     * 网络错误页面 layoutId
     */
    @LayoutRes
    private var netErrorLayoutId: Int = View.NO_ID

    /**
     * 重试 点击事件
     */
    private var onRetry: (() -> Unit)? = null

    /**
     * 加载页面 layoutId
     */
    fun setLoadingLayoutId(@LayoutRes layoutId: Int) {
        this.loadingLayoutId = layoutId
    }

    /**
     * 空页面 layoutId
     */
    fun setEmptyLayoutId(@LayoutRes layoutId: Int) {
        this.emptyLayoutId = layoutId
    }

    /**
     * 加载错误页面 layoutId
     */
    fun setErrorLayoutId(@LayoutRes layoutId: Int) {
        this.errorLayoutId = layoutId
    }

    /**
     * 网络错误页面 layoutId
     */
    fun setNetErrorLayoutId(@LayoutRes layoutId: Int) {
        this.netErrorLayoutId = layoutId
    }

    /**
     * 重试ids
     */
    fun setRetryIds(ids: IntArray?) {
        this.retryIds = ids
    }

    /**
     * 重试回调
     */
    fun setRetry(retry: () -> Unit) {
        this.onRetry = retry
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1 || childCount == 0) {
            throw UnsupportedOperationException("StateLayout only have one child view")
        }

        if (statusContainer.size == 0) {
            val view = getChildAt(0)
            setContentView(view)
        }
    }

    fun showStatus(status: PageStatus?, tag: String? = null) {
        if (status == currStatus || status == null) return

        val preStatus = this.currStatus
        val targetStatusView = getStatusView(status, tag) ?: return

        stateChangedHandler.onAdd(
            this@StateLayout,
            targetStatusView,
            status,
            tag
        )

        playLoadingLottie(targetStatusView, status)

        statusContainer.filter { it.key == preStatus }.forEach { entry ->
            stateChangedHandler.onRemove(
                this@StateLayout,
                entry.value.view,
                entry.key,
                entry.value.tag
            )
        }

        this.currStatus = status

        retryIds?.forEach { retryId ->
            val retryView = targetStatusView.findViewById(retryId) as View?
            retryView?.visibility = isShowRetry.then(View.VISIBLE, View.GONE)
            retryView?.doOnClick {
                onRetry?.invoke()
            }
        }
    }

    /**
     * 设置 content view
     */
    fun setContentView(view: View) {
        statusContainer[PageStatus.CONTENT] = StateInfo(view, view.javaClass.tagName)
    }

    /**
     * 是否显示重试按钮
     */
    fun showRetryButton(showRetry: Boolean) {
        this.isShowRetry = showRetry
    }

    /**
     * 显示加载中Page
     */
    fun showLoading(tag: String? = null) {
        showStatus(PageStatus.LOADING, tag)
    }

    /**
     * 显示空Page
     */
    fun showEmpty(tag: String? = null) {
        showStatus(PageStatus.EMPTY, tag)
    }

    /**
     * 显示错误Page
     */
    fun showError(tag: String? = null, isNetError: Boolean = false) {
        showStatus(isNetError.then(PageStatus.NET_ERROR, PageStatus.ERROR), tag)
    }

    fun showContent(tag: String? = null) {
        showStatus(PageStatus.CONTENT, tag)
        isLoaded = true
    }

    private fun getStatusView(status: PageStatus, tag: String?): View? {
        statusContainer[status]?.let { info ->
            info.tag = tag
            return info.view
        }

        val layoutId = when (status) {
            PageStatus.LOADING -> loadingLayoutId
            PageStatus.EMPTY -> emptyLayoutId
            PageStatus.ERROR -> errorLayoutId
            PageStatus.NET_ERROR -> netErrorLayoutId
            PageStatus.CONTENT -> View.NO_ID
        }

        if (layoutId == View.NO_ID) return null

        val stateLayoutView = LayoutInflater.from(context).inflate(layoutId, this, false)
        statusContainer[status] = StateInfo(stateLayoutView, tag)
        return stateLayoutView
    }

    private fun playLoadingLottie(loadingView: View?, status: PageStatus) {
        if (status != PageStatus.LOADING || loadingView == null) return

        if (loadingLottieView == null) {
            loadingLottieView = loadingView.findViewById(R.id.views_state_lottie)
            loadingLottieView?.setAnimation("state/loading.json")
        }

        loadingLottieView?.progress = 0f
        loadingLottieView?.playAnimation()
    }
}