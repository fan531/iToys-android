package com.itoys.expansion

/**
 * @author Fanfan Gu <a href="mailto:stefan.gufan@gmail.com">Contact me.</a>
 * @date 25/04/2022 01:02
 * @desc Boolean 扩展.
 */
infix fun <T> Boolean.then(value: T?) = if (this) value else null

fun <T> Boolean.then(value: T, default: T) = if (this) value else default

inline fun <T> Boolean.then(function: () -> T, default: T) = if (this) function() else default

inline fun <T> Boolean.then(function: () -> T, default: () -> T) = if (this) function() else default()

inline infix fun <reified T> Boolean.then(function: () -> T) = if (this) function() else null