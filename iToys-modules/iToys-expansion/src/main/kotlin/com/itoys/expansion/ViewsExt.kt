package com.itoys.expansion

import android.view.View

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 11/03/2023
 * @desc view 扩展.
 */

/** 连续点击时长 */
private var continuousClickTime = 600

/** 上次点击事件 */
private var lastClickTime = 0L

/** 上次点击标志 */
private var lastClickTag = 0

/**
 * view 点击.
 *
 * 防止连续点击.
 */
fun View.doOnClick(block: () -> Unit) {
    this.setOnClickListener {
        val currentTag = this.id
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime < continuousClickTime && lastClickTag == currentTag) {
            return@setOnClickListener
        }

        lastClickTime = currentTimeMillis
        lastClickTag = currentTag
        block()
    }
}

