buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}

plugins {
    `kotlin-dsl`

    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation("com.android.tools.build:gradle:7.3.1")
}

gradlePlugin {
    plugins {
        create("itoys") {
            id = "com.itoys"
            implementationClass = "com.itoys.IToysBuild"
        }
    }
}