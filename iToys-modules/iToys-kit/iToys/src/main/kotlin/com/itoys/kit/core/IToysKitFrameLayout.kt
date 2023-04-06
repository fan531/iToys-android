package com.itoys.kit.core

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 29/03/2023
 * @desc
 */
class IToysKitFrameLayout : FrameLayout, IToysKitViewInterface {

    constructor(context: Context) : super(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var mTitle: String = ""

    var title: String
        get() = mTitle
        set(value) {
            mTitle = value
        }
}