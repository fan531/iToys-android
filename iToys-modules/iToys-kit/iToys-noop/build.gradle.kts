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
    libraryConfig(
        project = project,
        namespaceId = "com.itoys.kit",
    )
}

dependencies {
    implementation(Depends.kotlin_stdlib)
    implementation(Modules.iToysUtils(project))
}