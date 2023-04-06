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
    fun iToysUtils(project: Project) = project.project(":iToys-modules:iToys-utils")

    /** iToys kit */
    fun iToysKit(project: Project) = project.project(":iToys-modules:iToys-kit:iToys")
    fun iToysKitNoop(project: Project) = project.project(":iToys-modules:iToys-kit:iToys-noop")

    fun iToysEnvAnnotation(project: Project) = project.project(":iToys-modules:iToys-env:env-annotation")
    fun iToysEnvApi(project: Project) = project.project(":iToys-modules:iToys-env:env-api")
    fun iToysEnvIml(project: Project) = project.project(":iToys-modules:iToys-env:env-iml")

    /** 登录 */
}