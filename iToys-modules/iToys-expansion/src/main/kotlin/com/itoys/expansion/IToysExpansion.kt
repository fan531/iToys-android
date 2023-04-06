package com.itoys.expansion

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 12/03/2023
 * @desc 通用ktx 扩展
 */

/**
 * 在IO线程中启动
 */
infix fun Any.launchOnIO(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.IO).launch { block() }
}

val Activity.tagName: String
    get() = this.javaClass.tagName

val Activity.className: String
    get() = this.javaClass.name

val Fragment.tagName: String
    get() = this.javaClass.tagName

val Fragment.className: String
    get() = this.javaClass.name

val Class<out Any>.tagName: String
    get() = this.canonicalName ?: ""

val KClass<out Any>.tagName: String
    get() = this.java.canonicalName ?: ""