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

android { libraryConfig(project = project) }

dependencies {
    implementation(Jetpacks.appcompat)
    implementation(Jetpacks.activity)
    implementation(Jetpacks.fragment)
    implementation(Depends.autosize)
    implementation(Jetpacks.core)
    implementation(Depends.coroutines_android)
    implementation(Jetpacks.constraintlayout)
    implementation(Depends.immersionbar)
    implementation(Depends.immersionbar_ktx)
    implementation(Depends.kotlin_stdlib)
    implementation(Depends.titlebar)
    implementation(Jetpacks.recyclerview)
    implementation(Depends.refresh)
    implementation(Depends.refresh_header_classics)
    implementation(Depends.refresh_footer_classics)
    implementation(Jetpacks.cardview)
    implementation(Jetpacks.multidex)

    // lifecycle, view model, live data,
    implementation(Jetpacks.lifecycle)
    implementation(Jetpacks.view_model)
    implementation(Jetpacks.live_data)
    kapt(Jetpacks.lifecycle_compiler)
    implementation(Jetpacks.lifecycle_java8)

    implementation(Modules.iToysExpansion(project))
    implementation(Modules.iToysLogcat(project))
    implementation(Modules.iToysNetwork(project))
    implementation(Modules.iToysTheme(project))

    debugImplementation(Modules.iToysEnv(project))
    releaseImplementation(Modules.iToysEnvNoOp(project))
}