import com.itoys.AppConfig
import com.itoys.depends.Depends
import com.itoys.depends.Jetpacks
import com.itoys.extension.appConfig
import com.itoys.depends.Modules
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    id("com.itoys")

    kotlin("android")
    kotlin("kapt")
}

android {
    appConfig(project = project)

    android.applicationVariants.all {
        this.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val buildDate = SimpleDateFormat("yyyyMMddHHmm").format(Date())
                val outputFileName = "iToys_${this.versionName}_${buildDate}_${AppConfig.patchVersion}.apk"
                output.outputFileName = outputFileName
            }
    }
}

dependencies {
    implementation(Jetpacks.activity)
    implementation(Jetpacks.core)
    implementation(Jetpacks.appcompat)
    implementation(Depends.kotlin_stdlib)

    implementation(Modules.iToysBase(project))
    implementation(Modules.iToysExpansion(project))
    implementation(Modules.iToysTheme(project))
}