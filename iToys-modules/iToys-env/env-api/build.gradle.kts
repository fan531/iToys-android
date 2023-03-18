import com.itoys.depends.Depends
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
    implementation(Depends.kotlin_stdlib)
    implementation(Modules.iToysEnvAnnotation(project))
    ksp(Modules.iToysEnvAnnotation(project))
}