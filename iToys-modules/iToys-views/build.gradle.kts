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
    libraryConfig(project = project)
}

dependencies {
    implementation(Jetpacks.core)
    implementation(Jetpacks.constraintlayout)
    implementation(Jetpacks.material)

    implementation(Depends.brv)
    implementation(Depends.kotlin_stdlib)
    implementation(Depends.spin)

    implementation(Modules.iToysExpansion(project))
    implementation(Modules.iToysLogcat(project))
    implementation(Modules.iToysTheme(project))
    implementation(Modules.iToysUtils(project))
}