package com.itoys.views.statelayout

import android.view.View
import androidx.annotation.LayoutRes

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 15/04/2023
 * @desc
 */
object StateManager {

    /**
     * 重试ids
     */
    @JvmStatic
    var retryIds: IntArray? = null

    /**
     * 加载页面 layoutId
     */
    @LayoutRes
    @JvmStatic
    var loadingLayoutId: Int = View.NO_ID

    /**
     * 空页面 layoutId
     */
    @LayoutRes
    @JvmStatic
    var emptyLayoutId: Int = View.NO_ID

    /**
     * 加载错误页面 layoutId
     */
    @LayoutRes
    @JvmStatic
    var errorLayoutId: Int = View.NO_ID

    /**
     * 网络错误页面 layoutId
     */
    @LayoutRes
    @JvmStatic
    var netErrorLayoutId: Int = View.NO_ID

    /**
     * 处理缺省页状态变更
     */
    @JvmStatic
    var stateChangedHandler: StateChangedHandler = StateChangedHandler
}