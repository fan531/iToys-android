import com.itoys.depends.Depends

plugins {
    id("java-library")
    id("com.itoys")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Depends.ksp)
    implementation(Depends.kotlinpoet)
}