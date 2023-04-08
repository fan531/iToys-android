import com.itoys.depends.Depends
import com.itoys.depends.Jetpacks
import com.itoys.depends.Modules
import com.itoys.extension.libraryConfig

plugins {
    id("com.android.library")
    id("com.itoys")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    kotlin("android")
    kotlin("kapt")
}

android {
    libraryConfig(project = project)
}

dependencies {
    implementation(Jetpacks.core)
    implementation(Jetpacks.appcompat)
    implementation(Jetpacks.recyclerview)
    implementation(Jetpacks.constraintlayout)

    implementation(Depends.brv)
    implementation(Depends.kotlin_stdlib)

    implementation(Modules.iToysExpansion(project))
    implementation(Modules.iToysEnvAnnotation(project))
    implementation(Modules.iToysUtils(project))
    ksp(Modules.iToysEnvAnnotation(project))
}