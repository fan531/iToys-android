package com.itoys.image.glide.progress

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 19/04/2023
 * @desc 下载进度的监听
 */

//给函数起别名 便于调用和书写
// typealias OnProgressListener = ((isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) -> Unit)?

interface OnProgressListener {
    fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long)
}