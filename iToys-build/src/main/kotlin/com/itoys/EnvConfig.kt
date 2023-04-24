package com.itoys

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/10/2022
 * @desc 环境相关 config.
 */
object EnvConfig {

    /** ************* Http host ************* */
    const val prodApiHost = "https://prod-app.zwsu.cn/api/v1/"

    /** ************* umeng ************* */

    /** AppKey */
    const val umengDevAppKey = "6346dc0388ccdf4b7e477c2c"
    const val umengTestAppKey = "6346dc0388ccdf4b7e477c2c"
    const val umengUatAppKey = "6346dc0388ccdf4b7e477c2c"
    val umengReleaseAppKey = "6346dc0388ccdf4b7e477c2c"

    /** Master Secret */
    const val umengDevMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"
    const val umengTestMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"
    const val umengUatMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"
    const val umengReleaseMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"

    /** Message Secret */
    const val umengDevMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"
    const val umengTestMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"
    const val umengUatMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"
    const val umengReleaseMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"

    /** ************* 微信开放平台 ************* */
}