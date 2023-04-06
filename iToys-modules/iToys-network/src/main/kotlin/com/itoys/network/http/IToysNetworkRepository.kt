package com.itoys.network.http

import com.itoys.network.entity.ApiState
import com.itoys.network.entity.BaseEntity
import com.itoys.network.exception.ApiResultCode

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 18/03/2023
 * @desc
 */
open class IToysNetworkRepository {

    suspend fun <T : Any?> executeRequest(
        block: suspend () -> BaseEntity<T>,
    ): BaseEntity<T> {
        val result = block.invoke()

        if (result.errorCode != ApiResultCode.RESULT_NORMAL) {
            result.state = ApiState.Error
        }

        return result
    }

    suspend fun <T : Any?> executeRequestNotCheck(
        block: suspend () -> BaseEntity<T>,
    ): BaseEntity<T> {
        return block.invoke()
    }
}