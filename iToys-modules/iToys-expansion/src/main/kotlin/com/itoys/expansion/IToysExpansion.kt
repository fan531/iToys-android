package com.itoys.expansion

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 12/03/2023
 * @desc 通用ktx 扩展
 */

/**
 * Pair ktx 简便写法
 */
infix fun <A, B> A.with(that: B): Pair<A, B> = Pair(this, that)

/**
 * 在IO线程中启动
 */
infix fun Any.launchOnIO(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.IO).launch { block() }
}