import com.itoys.depends.Depends
import com.itoys.depends.Modules
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
    implementation(Depends.kotlin_stdlib)
    implementation(Depends.retrofit)
    implementation(Depends.okhttp)
    implementation(Depends.gson)
    implementation(Depends.jackson)
    implementation(Modules.iToysLogcat(project))
}