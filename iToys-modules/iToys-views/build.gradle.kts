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
        namespaceId = "com.itoys.views",
    )
}

dependencies {
    implementation(Jetpacks.core)
    implementation(Jetpacks.constraintlayout)
    implementation(Jetpacks.material)

    implementation(Depends.brv)
    implementation(Depends.kotlin_stdlib)
    implementation(Depends.lottie)
    implementation(Depends.spin)

    implementation(Modules.iToysTheme(project))
    implementation(Modules.iToysUtils(project))
}