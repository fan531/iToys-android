package com.itoys.env

import com.itoys.env.entity.EnvConfigEntity
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MUTABLE_LIST
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeSpec

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 16/03/2023
 * @desc
 */
private val configEntity = ClassName("com.itoys.env.entity", "EnvConfigEntity")

fun generatorEnvConfigClass(
    envConfigList: MutableList<EnvConfigEntity>,
): TypeSpec {
    val classBuilder = TypeSpec.objectBuilder("IToysEnvApiConfig")

    classBuilder.addProperty(envListPropertySpec())

    classBuilder.addFunction(initEnvConfigFunc(envConfigList))
    classBuilder.addFunction(envConfigListFunc())

    return classBuilder.build()
}

private fun envListPropertySpec(): PropertySpec {
    val configEntity = ClassName("com.itoys.env.entity", "EnvConfigEntity")
    val envList = PropertySpec.builder(
        "envList",
        MUTABLE_LIST.parameterizedBy(configEntity)
    ).addModifiers(KModifier.PRIVATE)
        .initializer("""mutableListOf()""")

    return envList.build()
}

private fun initEnvConfigFunc(envConfigList: MutableList<EnvConfigEntity>): FunSpec {
    val initEnvConfig = FunSpec.builder("initEnvConfig")
        .addModifiers(KModifier.PRIVATE)
        .addStatement("if (envList.size == 0) {")
        .addStatement(
            "envList.add(EnvConfigEntity(%S, %S, ${envConfigList[0].release}))",
            envConfigList[0].url,
            envConfigList[0].alias
        )
        .addStatement(
            "envList.add(EnvConfigEntity(%S, %S, ${envConfigList[1].release}))",
            envConfigList[1].url,
            envConfigList[1].alias
        )
        .addStatement(
            "envList.add(EnvConfigEntity(%S, %S, ${envConfigList[2].release}))",
            envConfigList[2].url,
            envConfigList[2].alias
        )
        .addStatement("}")
    return initEnvConfig.build()
}

private fun envConfigListFunc(): FunSpec {
    val initEnvConfig = FunSpec.builder("envConfigList")
        .returns(MUTABLE_LIST.parameterizedBy(configEntity))
        .addStatement("initEnvConfig()")
        .addStatement("")
        .addStatement("return envList")
    return initEnvConfig.build()
}

private fun prodApiUrlFunc(url: String): FunSpec {
    val prodApiHost = FunSpec.builder("prodApiUrl")
        .returns(STRING)
        .addStatement("return %S", url)
    return prodApiHost.build()
}