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
    libraryConfig(project = project)
}

dependencies {
    implementation(Jetpacks.material)
    implementation(Depends.kotlin_stdlib)
}