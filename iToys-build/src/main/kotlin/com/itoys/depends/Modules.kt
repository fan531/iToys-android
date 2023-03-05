package com.itoys.depends

import org.gradle.api.Project

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/10/2022
 * @desc
 */
object Modules {

    /** lib */
    fun libBase(project: Project) = project.project(":modules-lib:lib-base")
    fun libTheme(project: Project) = project.project(":modules-lib:lib-theme")

    /** 环境切换 */
    fun moduleEnvApi(project: Project) = project.project(":module-env:env-api")
    fun moduleEnv(project: Project) = project.project(":module-env:env-iml")

    /** 登录 */
}