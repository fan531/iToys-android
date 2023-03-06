package com.itoys.expansion

import java.math.BigDecimal

/**
 * @author Fanfan Gu <a href="mailto:stefan.gufan@gmail.com">Contact me.</a>
 * @date 25/04/2022 01:03
 * @desc 字符串扩展.
 */

/**
 * 判断字符串是否为null或全为空格
 */
fun CharSequence?.isBlank(): Boolean {
    return (null == this || this.toString().trimString().size() == 0 || this == "null")
}

fun CharSequence?.isNotBlank(): Boolean {
    return !isBlank()
}

/**
 * 判断字符串是否有效, 如果为空默认返回长度为0的字符串
 */
fun CharSequence?.invalid(invalid: String = ""): String {
    return if (isBlank()) {
        invalid
    } else {
        this.toString().trimString()
    }
}

/**
 * 字符串去掉空格, 如果字符串为空返回长度为0的字符串
 */
fun String?.trimString(): String {
    return this?.trim() ?: ""
}

/**
 * 字符串长度, 如果字符串为空返回0
 */
fun CharSequence?.size(): Int {
    return this?.length ?: 0
}

/**
 * @param addComma 是否需要添加逗号，默认不加
 * @param modeFloor 是否使用去尾法，默认true 1.5->1   2.8->2
 * @param decimalNum 小数点后位数
 */
fun String.formatNumber(
    addComma: Boolean = false,
    modeFloor: Boolean = true,
    decimalNum: Int? = DEFAULT_DECIMAL_NUMBER
): String = this.toBigDecimalWithNull().formatNumber(addComma, modeFloor, decimalNum)

fun String?.toBigDecimalWithNull(default: BigDecimal = BigDecimal.ZERO) =
    isNotBlank().then({
        try {
            this.invalid("0").toBigDecimal()
        } catch (e: NumberFormatException) {
            default
        }
    }, default)

fun String?.toIntWithNull(default: Int = 0) =
    isNotBlank().then({
        try {
            this.invalid("0").toInt()
        } catch (e: NumberFormatException) {
            default
        }
    }, default)

fun String?.toFloatWithNull(default: Float = 0f) =
    isNotBlank().then({
        try {
            this.invalid("0").toFloat()
        } catch (e: NumberFormatException) {
            default
        }
    }, default)

fun String?.toDoubleWithNull(default: Double = 0.00) =
    isNotBlank().then({
        try {
            this.invalid("0").toDouble()
        } catch (e: NumberFormatException) {
            default
        }
    }, default)

fun CharSequence?.isMobileSimple() =
    this.size() > 0 && "^[1]\\d{10}\$".toRegex().matches(this.invalid(""))

fun CharSequence?.isMobileExact() =
    this.size() > 0 && "^((13[0-9])|(14[0,1,4-8])|(15[^4])|(16[2,5,6,7])|(17[^9])|(18[0-9])|(19[^4]))\\d{8}\$".toRegex()
        .matches(this.invalid(""))

fun CharSequence?.hidePhoneNum() = this?.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1****$2")

fun CharSequence?.hideCommentName() =
    if (size() > 1) {
        this?.substring(0, 1) + "**"
    } else {
        this
    }


fun CharSequence?.hideNickName() =
    if (size() > 12) {
        "${this?.substring(0, 12)}..."
    } else {
        this
    }

