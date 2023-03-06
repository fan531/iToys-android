package com.itoys.expansion

/**
 * if low using default value instead.
 */
fun Int.ifLow(defaultValue: Int = 0): Int = (this < defaultValue).then(defaultValue, this)

/**
 * if zero using default value instead.
 */
fun Int.ifZero(defaultValue: Int = 40): Int = (this == 0).then(defaultValue, this)