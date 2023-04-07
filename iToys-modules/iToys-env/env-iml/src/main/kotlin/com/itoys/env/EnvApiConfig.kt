package com.itoys.env

import com.itoys.env.annotation.Env
import com.itoys.env.annotation.Module

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 14/03/2023
 * @desc
 */
@Module(alias = "iToysApiConfig")
class EnvApiConfig(
    @Env(url = "https://api.zwsu.cn/api/", alias = "开发环境")
    val debugUrl: String,
    @Env(url = "https://api.zwsu.cn/api/", alias = "测试环境")
    val testUrl: String,
    @Env(url = "https://api.zwsu.cn/api/", alias = "生产环境", release = true)
    val releaseUrl: String,
    @Env(url = "http://127.0.0.1:4523/m1/2554044-0-default/", alias = "本地 Mock", release = false)
    val mockUrl: String,
    @Env(url = "", alias = "自定义", release = false)
    val customUrl: String,
)