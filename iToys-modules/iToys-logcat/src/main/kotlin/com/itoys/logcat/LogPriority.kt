package com.itoys.logcat

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 12/03/2023
 * @desc An enum for log priorities that map to [android.util.Log]
 * priority constants without a direct import.
 */
enum class LogPriority(
    val priorityInt: Int
) {
    NONE(-1),
    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6),
    ASSERT(7);
}
