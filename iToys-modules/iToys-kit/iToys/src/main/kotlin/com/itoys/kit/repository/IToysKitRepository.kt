package com.itoys.kit.repository

import com.itoys.utils.mmkv.MMKVOwner
import com.itoys.utils.mmkv.mmkvInt
import com.itoys.utils.mmkv.mmkvString
import com.tencent.mmkv.MMKV

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 01/04/2023
 * @desc kit 存储库
 */
object IToysKitRepository : MMKVOwner {

    override val mmkv: MMKV
        get() = MMKV.mmkvWithID("itoys-kit")

    /** kit view 上一次坐标点 x */
    var kitViewLastPositionX: Int by mmkvInt()

    /** kit view 上一次坐标点 y */
    var kitViewLastPositionY: Int by mmkvInt()

    /** kit network alias */
    var kitNetworkAlias: String? by mmkvString()

    /** kit network url */
    var kitNetworkUrl: String? by mmkvString()
}