package com.itoys.logcat

import okio.buffer
import okio.sink
import java.io.File

/**
 * A [LogcatLogger] that always logs and delegates to [println] concatenating
 * the tag and message, separated by a space. Alternative to [AndroidLogcatLogger]
 * when running on a JVM.
 */
object PrintLogger : LogcatLogger {

    override fun log(priority: LogPriority, tag: String, message: String) {
        println("$tag -> $message")
    }

    override fun logFile(priority: LogPriority, logFile: File, message: String) {
        logFile.sink(append = true).buffer().writeUtf8(message).writeUtf8("\n").flush()
    }
}
