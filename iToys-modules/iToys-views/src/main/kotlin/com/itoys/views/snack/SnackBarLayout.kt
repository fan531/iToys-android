package com.itoys.views.snack

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import com.google.android.material.R
import com.itoys.expansion.layoutInflater
import com.itoys.expansion.then
import com.itoys.views.databinding.ItoysViewLayoutSnackBarBinding

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 11/04/2023
 * @desc
 */
class SnackBarLayout : LinearLayout {

    private var mMaxWidth = 0
    private var mMaxInlineActionWidth = 0
    private lateinit var mSnackBarBinding: ItoysViewLayoutSnackBarBinding

    private var mOnLayoutChangeListener: OnLayoutChangeListener? = null
    private var mOnAttachStateChangeListener: OnAttachStateChangeListener? = null

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SnackbarLayout
        )
        mMaxWidth = typedArray.getDimensionPixelSize(
            R.styleable.SnackbarLayout_android_maxWidth,
            -1
        )
        mMaxInlineActionWidth = typedArray.getDimensionPixelSize(
            R.styleable.SnackbarLayout_maxActionInlineWidth,
            -1
        )
        typedArray.recycle()

        isClickable = true
        val layoutInflater = context.layoutInflater ?: LayoutInflater.from(context)
        mSnackBarBinding = ItoysViewLayoutSnackBarBinding.inflate(layoutInflater, this)
        ViewCompat.setAccessibilityLiveRegion(this, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE)
        ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES)
    }

    fun getMessageView(): AppCompatTextView {
        return mSnackBarBinding.viewsTvSnackBarText
    }

    fun getActionView(): AppCompatButton {
        return mSnackBarBinding.viewsBtnSnackBarAction
    }

    fun setOnLayoutChangeListener(listener: OnLayoutChangeListener?) {
        this.mOnLayoutChangeListener = listener
    }

    fun setOnAttachStateChangeListener(listener: OnAttachStateChangeListener) {
        this.mOnAttachStateChangeListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mMaxWidth in 1 until measuredWidth) {
            super.onMeasure(
                MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY),
                heightMeasureSpec
            )
        }

        val multiLineVPadding = resources.getDimensionPixelSize(
            R.dimen.design_snackbar_padding_vertical_2lines
        )
        val singleLineVPadding = resources.getDimensionPixelSize(
            com.google.android.material.R.dimen.design_snackbar_padding_vertical
        )
        val isMultiLine = getMessageView().layout.lineCount > 1
        val remeasure =
            if (mMaxInlineActionWidth in 0 until getActionView().measuredWidth && isMultiLine) {
                updateViewsWithinLayout(
                    VERTICAL,
                    multiLineVPadding,
                    (multiLineVPadding - singleLineVPadding)
                )
            } else {
                val messagePadding = isMultiLine.then(multiLineVPadding, singleLineVPadding)
                updateViewsWithinLayout(HORIZONTAL, messagePadding, messagePadding)
            }

        if (remeasure) super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (changed) {
            mOnLayoutChangeListener?.onLayoutChange(this, l, t, r, b)
        }
    }

    fun animateChildrenIn(delay: Int, duration: Int) {
        animateChildrenIn(getMessageView(), delay, duration)

        if (getActionView().visibility == View.VISIBLE) {
            animateChildrenIn(getActionView(), delay, duration)
        }
    }

    private fun animateChildrenIn(view: View, delay: Int, duration: Int) {
        view.alpha = 0f
        ViewCompat.animate(view).alpha(1f)
            .setDuration(duration.toLong())
            .setStartDelay(delay.toLong())
            .start()
    }

    fun animateChildrenOut(delay: Int, duration: Int) {
        animateChildrenOut(getMessageView(), delay, duration)

        if (getActionView().visibility == View.VISIBLE) {
            animateChildrenOut(getActionView(), delay, duration)
        }
    }

    private fun animateChildrenOut(view: View, delay: Int, duration: Int) {
        view.alpha = 1f
        ViewCompat.animate(view).alpha(0f)
            .setDuration(duration.toLong())
            .setStartDelay(delay.toLong())
            .start()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mOnAttachStateChangeListener?.onViewAttachedToWindow(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mOnAttachStateChangeListener?.onViewDetachedFromWindow(this)
    }

    private fun updateViewsWithinLayout(
        orientation: Int,
        messagePadTop: Int,
        messagePadBottom: Int,
    ): Boolean {
        var changed = false
        if (orientation != getOrientation()) {
            setOrientation(orientation)
            changed = true
        }

        if (getMessageView().paddingTop != messagePadTop || getMessageView().paddingBottom != messagePadBottom) {
            updateTopBottomPadding(getMessageView(), messagePadTop, messagePadBottom)
            changed = true
        }

        return changed
    }

    private fun updateTopBottomPadding(view: View, paddingTop: Int, paddingBottom: Int) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(
                view,
                ViewCompat.getPaddingStart(view),
                paddingTop,
                ViewCompat.getPaddingEnd(view),
                paddingBottom
            )
        } else {
            view.setPadding(view.paddingLeft, top, view.paddingRight, paddingBottom)
        }
    }

    interface OnLayoutChangeListener {
        fun onLayoutChange(view: View, left: Int, top: Int, right: Int, bottom: Int)
    }

    interface OnAttachStateChangeListener {
        fun onViewAttachedToWindow(view: View)

        fun onViewDetachedFromWindow(view: View)
    }
}