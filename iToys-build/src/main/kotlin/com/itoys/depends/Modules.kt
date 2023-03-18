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
    fun iToysLogcat(project: Project) = project.project(":iToys-modules:iToys-logcat")
    fun iToysNetwork(project: Project) = project.project(":iToys-modules:iToys-network")
    fun iToysTheme(project: Project) = project.project(":iToys-modules:iToys-theme")

    fun iToysEnvAnnotation(project: Project) = project.project(":iToys-modules:iToys-env:env-annotation")
    fun iToysEnvApi(project: Project) = project.project(":iToys-modules:iToys-env:env-api")
    fun iToysEnv(project: Project) = project.project(":iToys-modules:iToys-env:env")
    fun iToysEnvNoOp(project: Project) = project.project(":iToys-modules:iToys-env:env-noop")

    /** 登录 */
}