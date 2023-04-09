package com.itoys.views.loading.animation

import android.util.Property

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc The class on which the Property is declared.
 *
 * An implementation of [Property] to be used specifically with fields of type
 * <code>int</code>. This type-specific subclass enables performance benefit by allowing
 * calls to a [set] function that takes the primitive
 * <code>int</code> type and avoids autoboxing and other overhead associated with the
 * <code>Int</code> class.
 */
abstract class IntProperty<T>(
    name: String
) : Property<T, Int>(Int::class.java, name) {

    /**
     * A type-specific override of the [set] that is faster when dealing
     * with fields of type <code>int</code>.
     */
    abstract fun setValue(`object`: T, value: Int)

    override fun set(`object`: T, value: Int) {
        setValue(`object`, value)
    }
}