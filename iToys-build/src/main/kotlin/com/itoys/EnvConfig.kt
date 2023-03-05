package com.itoys

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/10/2022
 * @desc 环境相关 config.
 */
object EnvConfig {

    /** ************* Http host ************* */
    val devEnvHost = "https://dev-app.zwsu.cn/api/v1/"
    val testEnvHost = "https://test-app.zwsu.cn/api/v1/"
    val uatEnvHost = "https://uat-app.zwsu.cn/api/v1/"
    val prodEnvHost = "https://prod-app.zwsu.cn/api/v1/"

    /** ************* umeng ************* */

    /** AppKey */
    val umengDevAppKey = "6346dc0388ccdf4b7e477c2c"
    val umengTestAppKey = "6346dc0388ccdf4b7e477c2c"
    val umengUatAppKey = "6346dc0388ccdf4b7e477c2c"
    val umengReleaseAppKey = "6346dc0388ccdf4b7e477c2c"

    /** Master Secret */
    val umengDevMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"
    val umengTestMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"
    val umengUatMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"
    val umengReleaseMasterSecret = "90orenfkjr717uige9zmumqdyqriwwh3"

    /** Message Secret */
    val umengDevMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"
    val umengTestMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"
    val umengUatMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"
    val umengReleaseMessageSecret = "6cba3b458ef0503f0ba6a611fd3d8478"

    /** ************* 微信开放平台 ************* */
}