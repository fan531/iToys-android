import com.itoys.extension.appConfig
import com.itoys.depends.Modules

plugins {
    id("com.android.application")
    id("com.itoys")

    kotlin("android")
    kotlin("kapt")
}

android {
    appConfig(project = project)
}

dependencies {
    implementation(Modules.libTheme(project))
}