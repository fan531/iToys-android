package com.itoys.network.http

import com.google.gson.JsonParseException
import com.itoys.network.exception.ApiResultCode
import com.itoys.network.exception.ResultException
import com.itoys.network.json.JsonMapper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/03/2023
 * @desc
 */
private val jsonMediaType: MediaType by lazy {
    "application/json; charset=utf-8".toMediaType()
}

internal fun argsToMap(vararg args: Pair<String, Any>): HashMap<String, Any> {
    return hashMapOf<String, Any>().apply {
        args.forEach {
            put(it.first, it.second)
        }
    }
}

internal fun argsToBody(vararg args: Pair<String, Any>): RequestBody {
    val argsMap = hashMapOf<String, Any>().apply {
        args.forEach {
            put(it.first, it.second)
        }
    }

    return JsonMapper.objToString(argsMap).toRequestBody(jsonMediaType)
}

internal fun argsMapToBody(argsMap: HashMap<String, Any>): RequestBody {
    return JsonMapper.objToString(argsMap).toRequestBody(jsonMediaType)
}

internal fun argsToJson(vararg args: Pair<String, Any>): String {
    val argsMap = hashMapOf<String, Any>().apply {
        args.forEach {
            put(it.first, it.second)
        }
    }

    return JsonMapper.objToString(argsMap)
}

internal fun Throwable.handlerException(): ResultException {
    return when (this) {
        is ResultException -> this
        is HttpException -> {
            when (code()) {
                ApiResultCode.FORBIDDEN -> {
                    ResultException(code(), "请求被拒绝, 请稍后重试")
                }

                ApiResultCode.UNAUTHORIZED -> {
                    ResultException(code(), "登录信息已失效, 请重新登录")
                }

                ApiResultCode.NOT_FOUND -> {
                    ResultException(code(), "请求找不到了, 请稍后重试")
                }

                ApiResultCode.REQUEST_TIMEOUT,
                ApiResultCode.GATEWAY_TIMEOUT -> {
                    ResultException(code(), "请求接口超时, 请稍后重试")
                }

                ApiResultCode.INTERNAL_SERVER_ERROR,
                ApiResultCode.BAD_GATEWAY -> {
                    ResultException(code(), "服务出现异常, 请稍后重试")
                }

                ApiResultCode.SERVICE_UNAVAILABLE -> {
                    ResultException(code(), "请求接口出现异常, 请稍后重试")
                }

                else -> ResultException(code(), "请求接口出现异常, 请稍后重试")
            }
        }

        is JsonParseException, is JSONException, is ParseException -> {
            ResultException(ApiResultCode.PARSE_ERROR, "解析数据出现异常, 请排查错误日志")
        }

        is ConnectException, is UnknownHostException, is UnknownServiceException -> {
            ResultException(ApiResultCode.NETWORK_NOT_CONNECTION, "网络连接出现异常, 请检查网络")
        }

        is SocketException -> {
            ResultException(
                ApiResultCode.NETWORK_NOT_CONNECTION,
                "网络连接出现异常, 请检查网络和权限配置"
            )
        }

        is SocketTimeoutException -> {
            ResultException(ApiResultCode.REQUEST_TIMEOUT, "请求接口超时, 请稍后重试")
        }

        is SSLHandshakeException -> {
            ResultException(ApiResultCode.SSL_ERROR, "SSL证书出现异常, 请检查SSL证书")
        }

        else -> ResultException(ApiResultCode.UNKNOWN, msg = message)
    }
}