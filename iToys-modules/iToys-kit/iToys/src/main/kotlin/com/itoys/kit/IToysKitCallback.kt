package com.itoys.kit

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 26/03/2023
 * @desc
 */
interface IToysKitCallback {

    fun onCpuCallBack(value: Float, cpuFilePath: String)

    fun onFpsCallBack(value: Float, fpsFilePath: String)

    fun onMemoryCallBack(value: Float, memoryFIlePath: String)

    fun onNetworkCallBack()
}