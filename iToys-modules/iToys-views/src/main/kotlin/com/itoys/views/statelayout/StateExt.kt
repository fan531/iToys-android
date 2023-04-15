package com.itoys.views.statelayout

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

fun Activity.addStateLayout(
    @LayoutRes loadingLayoutId: Int = StateManager.loadingLayoutId,
    @LayoutRes emptyLayoutId: Int = StateManager.emptyLayoutId,
    @LayoutRes errorLayoutId: Int = StateManager.errorLayoutId,
    @LayoutRes netErrorLayoutId: Int = StateManager.netErrorLayoutId,
    retryIds: IntArray? = StateManager.retryIds,
    showRetry: Boolean = true,
    retry: () -> Unit = {}
): StateLayout {
    val contentView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    return contentView.addStateLayout(
        loadingLayoutId,
        emptyLayoutId,
        errorLayoutId,
        netErrorLayoutId,
        retryIds,
        showRetry,
        retry
    )
}

fun Fragment.addStateLayout(
    @LayoutRes loadingLayoutId: Int = StateManager.loadingLayoutId,
    @LayoutRes emptyLayoutId: Int = StateManager.emptyLayoutId,
    @LayoutRes errorLayoutId: Int = StateManager.errorLayoutId,
    @LayoutRes netErrorLayoutId: Int = StateManager.netErrorLayoutId,
    retryIds: IntArray? = StateManager.retryIds,
    showRetry: Boolean = true,
    retry: () -> Unit = {}
): StateLayout {
    val stateLayout = requireView().addStateLayout(
        loadingLayoutId,
        emptyLayoutId,
        errorLayoutId,
        netErrorLayoutId,
        retryIds,
        showRetry,
        retry
    )

    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            val parent = stateLayout.parent as ViewGroup?
            parent?.removeView(stateLayout)
            lifecycle.removeObserver(this)
            super.onDestroy(owner)
        }
    })

    return stateLayout
}

fun View.addStateLayout(
    @LayoutRes loadingLayoutId: Int = StateManager.loadingLayoutId,
    @LayoutRes emptyLayoutId: Int = StateManager.emptyLayoutId,
    @LayoutRes errorLayoutId: Int = StateManager.errorLayoutId,
    @LayoutRes netErrorLayoutId: Int = StateManager.netErrorLayoutId,
    retryIds: IntArray? = StateManager.retryIds,
    showRetry: Boolean = true,
    retry: () -> Unit = {}
): StateLayout {
    val parent = parent as ViewGroup
    if (parent is ViewPager || parent is RecyclerView) {
        throw UnsupportedOperationException("You should using StateLayout wrap [ $this ] in layout when parent is ViewPager or RecyclerView")
    }

    val viewIndex = parent.indexOfChild(this)
    val stateLayout = StateLayout(context)
    stateLayout.id = View.generateViewId()
    stateLayout.setLoadingLayoutId(loadingLayoutId)
    stateLayout.setEmptyLayoutId(emptyLayoutId)
    stateLayout.setErrorLayoutId(errorLayoutId)
    stateLayout.setNetErrorLayoutId(netErrorLayoutId)
    stateLayout.setRetryIds(retryIds)
    stateLayout.setRetry(retry)

    val stateLayoutParams = layoutParams
    parent.removeView(this)
    parent.addView(stateLayout, viewIndex, stateLayoutParams)

    when (this) {
        is ConstraintLayout -> {
            val contentViewLayoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            stateLayout.addView(this, contentViewLayoutParams)
        }

        else -> {
            stateLayout.addView(this)
        }
    }

    stateLayout.showRetryButton(showRetry)
    stateLayout.setContentView(this)
    return stateLayout
}