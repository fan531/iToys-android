package com.itoys.env

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSBuiltIns
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.symbol.Modifier
import com.itoys.env.entity.EnvConfigEntity
import com.squareup.kotlinpoet.FileSpec

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 14/03/2023
 * @desc
 */
class IToysKspVisitor(
    private val environment: SymbolProcessorEnvironment,
    private val builtIns: KSBuiltIns,
) : KSVisitorVoid() {

    private lateinit var className: String
    private lateinit var packageName: String
    private val envConfigList: MutableList<EnvConfigEntity> = mutableListOf()

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        environment.logger.warn("visitClassDeclaration is start...")
        if (classDeclaration.isDataClass()) return
        className = classDeclaration.qualifiedName?.asString() ?: return
        packageName = classDeclaration.packageName.asString()

        classDeclaration.getAllProperties().forEach {
            it.accept(this, Unit)
        }

        environment.logger.warn("codeGenerator is start...")
        environment.logger.warn("codeGenerator packageName: $packageName")
        environment.logger.warn("codeGenerator fileName: ${classDeclaration.simpleName.asString()}")

        packageName = "com.itoys.env.iml"
        className = "IToysEnvApiConfig"
        val fileSpec = FileSpec.builder(
            packageName = packageName,
            fileName = className
        ).apply {
            addType(generatorEnvConfigClass(envConfigList))
        }.build()

        environment.codeGenerator.createNewFile(
            dependencies = Dependencies(aggregating = false),
            packageName = packageName,
            fileName = className
        ).use {
            it.writer().use { writer ->
                fileSpec.writeTo(writer)
            }
        }

        environment.logger.warn("codeGenerator ending!")

        environment.logger.warn("all end!")
    }

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
        environment.logger.warn("visitPropertyDeclaration is start...")

        var url = ""
        var alias = ""
        var release = false
        var envConfigEntity: EnvConfigEntity

        property.annotations.forEach { kSAnnotated ->

            kSAnnotated.arguments.forEach { kSValueArgument ->
                when (kSValueArgument.name?.asString()) {
                    "url" -> url = kSValueArgument.value?.toString() ?: ""
                    "alias" -> alias = kSValueArgument.value?.toString() ?: ""
                    "release" -> release = (kSValueArgument.value as Boolean?) ?: false
                }
            }

            envConfigEntity = EnvConfigEntity(url, alias, release)
            environment.logger.warn(envConfigEntity.toString())
            envConfigList.add(envConfigEntity)
        }

        environment.logger.warn("visitPropertyDeclaration is end!")
    }

    private fun KSClassDeclaration.isDataClass() = modifiers.contains(Modifier.DATA)
}