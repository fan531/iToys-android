import com.itoys.depends.Depends
import com.itoys.depends.Jetpacks
import com.itoys.depends.Modules
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
        namespaceId = "com.itoys.utils",
    )
}

dependencies {
    implementation(Jetpacks.core)
    implementation(Jetpacks.appcompat)
    implementation(Jetpacks.material)
    implementation(Depends.mmkv)
    implementation(Depends.kotlin_stdlib)
}