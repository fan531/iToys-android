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
    implementation(Jetpacks.core)
    implementation(Jetpacks.appcompat)
    implementation(Depends.kotlin_stdlib)
    implementation(Jetpacks.recyclerview)
    implementation(Depends.refresh)
    implementation(Depends.refresh_header_classics)
    implementation(Depends.refresh_footer_classics)
}