package com.itoys.logcat

import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 12/03/2023
 * @desc
 */
/**
 * 异常 as log.
 */
fun Throwable.asLog(): String {
    val stringWriter = StringWriter(256)
    val printWriter = PrintWriter(stringWriter, false)
    printStackTrace(printWriter)
    printWriter.flush()
    return stringWriter.toString()
}
