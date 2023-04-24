import com.itoys.depends.Depends
import com.itoys.depends.Jetpacks
import com.itoys.extension.libraryConfig

plugins {
    id("com.android.library")
    id("com.itoys")
    kotlin("android")
    kotlin("kapt")
}

android {
    libraryConfig(
        project = project,
        namespaceId = "com.itoys.image",
    )
}

dependencies {
    implementation(Jetpacks.core)
    implementation(Jetpacks.appcompat)

    implementation(Depends.kotlin_stdlib)
    implementation(Depends.glide)
    implementation(Depends.okhttp)
    implementation(Depends.okio)

    kapt(Depends.glide_compiler)
}