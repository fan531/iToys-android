package com.itoys.views.loading

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc
 */
class LoadingView : ProgressBar {

    private lateinit var mLoadingStyle: LoadingStyle
    private var mLoadingColor: Int = 0

    constructor(context: Context) : super(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingView,
            defStyleAttr,
            R.style.LoadingView
        )
        mLoadingStyle =
            LoadingStyle.values()[typedArray.getInt(R.styleable.LoadingView_loading_style, 0)]
        mLoadingColor = typedArray.getColor(R.styleable.LoadingView_loading_color, Color.WHITE)
        typedArray.recycle()

        init()
        isIndeterminate = true
    }

    private fun init() {

    }

    override fun setIndeterminateDrawable(d: Drawable?) {
        super.setIndeterminateDrawable(d)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {

        }
    }

    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
        if (screenState == View.SCREEN_STATE_OFF) {

        }
    }
}