package com.itoys.network.exception

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/03/2023
 * @desc 请求结果异常基类
 */
class ResultException(var errCode: Int?, var msg: String?) : Exception(msg)