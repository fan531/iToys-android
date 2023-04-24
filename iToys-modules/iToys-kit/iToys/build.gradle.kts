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
        namespaceId = "com.itoys.kit",
    )
}

dependencies {
    implementation(Jetpacks.appcompat)
    implementation(Jetpacks.activity)
    implementation(Jetpacks.core)
    implementation(Jetpacks.fragment)
    implementation(Jetpacks.recyclerview)

    implementation(Depends.brv)
    implementation(Depends.coroutines_android)
    implementation(Depends.immersionbar)
    implementation(Depends.immersionbar_ktx)
    implementation(Depends.kotlin_stdlib)
    implementation(Depends.mmkv)
    implementation(Depends.titlebar)

    // lifecycle, view model, live data,
    implementation(Jetpacks.lifecycle)
    implementation(Jetpacks.view_model)
    implementation(Jetpacks.live_data)
    kapt(Jetpacks.lifecycle_compiler)
    implementation(Jetpacks.lifecycle_java8)

    implementation(Modules.iToysEnvIml(project))
    implementation(Modules.iToysNetwork(project))
    implementation(Modules.iToysUtils(project))
}