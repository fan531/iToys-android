package com.itoys

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class IToysBuild : Plugin<Project> {

    private companion object {
        const val implementation = "implementation"
        const val debugImplementation = "debugImplementation"
        const val fieldTypeString = "String"
        const val fieldTypeBoolean = "boolean"
        const val fieldTypeInt = "int"
    }

    override fun apply(target: Project) {
        println("> BuildPlugin :applyProject ${target.name}")

        when (target.name) {
            "iToys-network" -> {
                val libExtension = libExtension(target)
                libExtension.buildTypes.forEach { buildType ->
                    buildType.buildConfigField(
                        type = fieldTypeString,
                        name = "PROD_API_URL",
                        value = "\"${EnvConfig.prodApiHost}\""
                    )
                }
            }
        }
    }

    private fun libExtension(target: Project): LibraryExtension {
        return target.extensions.getByType(LibraryExtension::class.java)
    }
}