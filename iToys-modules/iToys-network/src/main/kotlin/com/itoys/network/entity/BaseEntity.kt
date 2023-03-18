package com.itoys.network.entity

import com.google.gson.annotations.SerializedName
import com.itoys.network.exception.ResultException

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/03/2023
 * @desc 结果请求基类
 */

class BaseEntity<T>(
    @SerializedName("code")
    var errorCode: Int = -1,
    @SerializedName("msg")
    var msg: String? = null,
    @SerializedName("data")
    var data: T? = null,
    @SerializedName("timestamp")
    var timestamp: Long = 0,
    var state: ApiState = ApiState.Success,
)

class PageEntity<T>(
    @SerializedName("pageNum")
    val pageNum: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("current")
    val current: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("records")
    val list: List<T>?,
)

sealed class ApiResultEntity<out T : Any?> {

    data class Success<out T : Any?>(val data: T) : ApiResultEntity<T>()

    data class Error(val exception: ResultException) : ApiResultEntity<Nothing>()
}

/**
 * api 请求状态
 */
enum class ApiState {
    Success, Error
}