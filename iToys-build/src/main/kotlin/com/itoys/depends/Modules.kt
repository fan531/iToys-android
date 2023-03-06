package com.itoys.depends

import org.gradle.api.Project

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/10/2022
 * @desc
 */
object Modules {

    /** iToys modules */
    fun iToysBase(project: Project) = project.project(":iToys-modules:iToys-base")
    fun iToysExpansion(project: Project) = project.project(":iToys-modules:iToys-expansion")
    fun iToysTheme(project: Project) = project.project(":iToys-modules:iToys-theme")

    /** 环境切换 */
    fun moduleEnvApi(project: Project) = project.project(":iToys-env:env-api")
    fun moduleEnv(project: Project) = project.project(":iToys-env:env-iml")

    /** 登录 */
}