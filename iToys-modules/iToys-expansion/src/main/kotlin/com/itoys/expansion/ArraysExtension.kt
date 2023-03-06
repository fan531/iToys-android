package com.itoys.expansion

/**
 * @author Fanfan Gu <a href="mailto:stefan.gufan@gmail.com">Contact me.</a>
 * @date 25/04/2022 01:03
 * @desc 数组扩展.
 */

fun ByteArray?.isEmpty(): Boolean {
    return this == null || (this?.size ?: 0) <= 0
}