package com.itoys

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer

class IToysBuild : Plugin<Project> {

    private companion object {
        const val implementation = "implementation"
    }

    override fun apply(target: Project) {
        println("> BuildPlugin :applyProject ${target.name}")
        // target.plugins.added(target)
    }

    /**
     * build.
     */
    private fun PluginContainer.added(target: Project) {
        /*whenPluginAdded {
            // println(it)
        }

        all { plugin ->
            if (plugin !is AppPlugin) {
                return@all
            }

            val appExtension = target.extensions.getByType(AppExtension::class.java)
            println(appExtension.defaultConfig)
            appExtension.run {
                this.applicationVariants.all { variant ->
                    println("> Variant :variantName ${variant.buildType.name}")

                    when (variant.buildType.name) {
                        *//*"debug" -> {
                            target.dependencies.add(implementation, Modules.moduleEnv(target))
                        }*//*
                    }
                }
            }
        }*/

        /*all { plugin ->
            if (plugin !is AppPlugin) {
                return@all
            }

            val appExtension = target.extensions.getByType(AppExtension::class.java)
            println(appExtension.defaultConfig)
            appExtension.run {
                this.applicationVariants.all { variant ->
                    println("> Variant : variantName: ${variant.buildType.name}")

                    when (variant.buildType.name) {
                        *//*"debug" -> {
                            target.dependencies.add(implementation, Modules.moduleEnv(target))
                        }*//*
                    }
                }
            }
        }*/
    }
}