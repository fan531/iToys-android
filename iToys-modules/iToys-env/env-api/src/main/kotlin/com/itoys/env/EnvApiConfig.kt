package com.itoys.env

import com.itoys.env.annotation.Env
import com.itoys.env.annotation.Module
import com.itoys.env.api.BuildConfig

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
)