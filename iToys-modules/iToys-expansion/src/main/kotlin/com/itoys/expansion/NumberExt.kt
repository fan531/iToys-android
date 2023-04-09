package com.itoys.expansion

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @author Fanfan Gu <a href="mailto:stefan.gufan@gmail.com">Contact me.</a>
 * @date 25/04/2022 01:03
 * @desc 数字扩展.
 */

/**
 * 默认保留小数点后10位
 */
const val DEFAULT_DECIMAL_NUMBER = 2

/**
 * 默认分隔符为千分位
 */
const val DEFAULT_SEPARATE_NUMBER = 3


/**
 * @param addComma 是否需要添加逗号，默认不加
 * @param modeFloor 是否使用去尾法，默认true 1.5->1   2.8->2
 * @param decimalNum 小数点后位数
 */
fun Number.formatNumber(
    addComma: Boolean = false,
    modeFloor: Boolean = true,
    decimalNum: Int? = DEFAULT_DECIMAL_NUMBER
): String {
    var decimal = decimalNum
    if (decimal == null) {
        decimal = DEFAULT_DECIMAL_NUMBER
    }
    val decimalFormat = DecimalFormat()
    decimalFormat.maximumFractionDigits = decimal
    decimalFormat.groupingSize = if (addComma) DEFAULT_SEPARATE_NUMBER else 0
    if (modeFloor) decimalFormat.roundingMode = RoundingMode.FLOOR
    return decimalFormat.format(this)
}

/**
 * if low using default value instead.
 */
fun Int.thanLess(defaultValue: Int = 0): Int = (this < defaultValue).then(defaultValue, this)