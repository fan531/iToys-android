package com.itoys.utils.mmkv.property

import com.itoys.utils.mmkv.MMKVOwner
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 01/04/2023
 * @desc mmkv 委托
 */
class MMKVProperty<V>(
    private val decode: MMKV.(String, V) -> V,
    private val encode: MMKV.(String, V) -> Boolean,
    private val defaultValue: V
) : ReadWriteProperty<MMKVOwner, V> {

    override fun getValue(thisRef: MMKVOwner, property: KProperty<*>): V {
        return thisRef.mmkv.decode(property.name, defaultValue)
    }

    override fun setValue(thisRef: MMKVOwner, property: KProperty<*>, value: V) {
        thisRef.mmkv.encode(property.name, value)
    }
}