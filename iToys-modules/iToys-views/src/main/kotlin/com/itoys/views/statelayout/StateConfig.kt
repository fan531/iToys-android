package com.itoys.views.statelayout

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 15/04/2023
 * @desc 全局的缺省页布局配置
 */
object StateConfig {

    class Builder {

        /**
         * 加载页面 layoutId
         */
        fun setLoadingLayoutId(@LayoutRes layoutId: Int): Builder {
            StateManager.loadingLayoutId = layoutId
            return this
        }

        /**
         * 空页面 layoutId
         */
        fun setEmptyLayoutId(@LayoutRes layoutId: Int): Builder {
            StateManager.emptyLayoutId = layoutId
            return this
        }

        /**
         * 加载错误页面 layoutId
         */
        fun setErrorLayoutId(@LayoutRes layoutId: Int): Builder {
            StateManager.errorLayoutId = layoutId
            return this
        }

        /**
         * 网络错误页面 layoutId
         */
        fun setNetErrorLayoutId(@LayoutRes layoutId: Int): Builder {
            StateManager.netErrorLayoutId = layoutId
            return this
        }

        /**
         * 重试ids
         */
        fun setRetryIds(@IdRes vararg ids: Int): Builder {
            StateManager.retryIds = ids
            return this
        }

        /**
         * State change handler.
         */
        fun setStateChangedHandler(handler: StateChangedHandler): Builder {
            StateManager.stateChangedHandler = handler
            return this
        }

        /**
         * 设置默认布局
         */
        fun setDefaultId(): Builder {
            StateManager.loadingLayoutId = R.layout.itoys_view_layout_state_loading
            StateManager.emptyLayoutId = R.layout.itoys_view_layout_state_empty
            StateManager.errorLayoutId = R.layout.itoys_view_layout_state_error
            StateManager.netErrorLayoutId = R.layout.itoys_view_layout_state_net_error
            StateManager.retryIds = intArrayOf(R.id.views_btn_retry)
            return this
        }

        fun build(): Builder {
            return this
        }
    }
}