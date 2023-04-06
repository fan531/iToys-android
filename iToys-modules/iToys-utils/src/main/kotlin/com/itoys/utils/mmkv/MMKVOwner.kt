package com.itoys.utils.mmkv

import com.itoys.utils.mmkv.property.MMKVProperty
import com.tencent.mmkv.MMKV

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 01/04/2023
 * @desc mmkv 委托
 */

interface MMKVOwner {

    val mmkv: MMKV
        get() = MMKV.defaultMMKV()
}

fun mmkvBoolean(default: Boolean = false) = MMKVProperty(MMKV::decodeBool, MMKV::encode, default)

fun mmkvDouble(default: Double = 0.00) = MMKVProperty(MMKV::decodeDouble, MMKV::encode, default)

fun mmkvFloat(default: Float = 0F) = MMKVProperty(MMKV::decodeFloat, MMKV::encode, default)

fun mmkvInt(default: Int = 0) = MMKVProperty(MMKV::decodeInt, MMKV::encode, default)

fun mmkvLong(default: Long = 0L) = MMKVProperty(MMKV::decodeLong, MMKV::encode, default)

fun mmkvString(default: String = "") = MMKVProperty(MMKV::decodeString, MMKV::encode, default)