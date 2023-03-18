package com.itoys.env.annotation

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 13/03/2023
 * @desc
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class Env(
    val alias: String = "",
    val release: Boolean = false,
    val url: String,
)
