#!/usr/bin/env kscript
@file:DependsOn("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.41")
@file:Include("utils.kt")

val moduleId = args[0]
val packageName = args[1]

forward(moduleId)

// .gitignore
file(".gitignore", """
    /build
    """.trimIndent()
)

// build.gradle.kts
file("build.gradle.kts", """
    plugins {
        id("com.android.library")
        id("kotlin-android")
    }
    
    apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/android-build-lib.gradle")
    apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/java-8-android.gradle")
    apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-android-ext.gradle")
    apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/kt-source-sets-android.gradle")
    apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/mvn-publish.gradle")
    
    dependencies {
        api(Deps.Kotlin.stdlib)
    }
    """.trimIndent()
)

// manifest
file("src/main/AndroidManifest.xml", """
    <manifest package="$packageName" />
    """.trimIndent()
)

// src dirs
val packagePath = packageName.replace(".", "/")
ensureDirs("/src/main/kotlin/$packagePath")
ensureDirs("/src/androidTest/kotlin/$packagePath")
ensureDirs("/src/test/kotlin/$packagePath")

// add to settings.gradle.kts
includeModuleInSettingsGradle(moduleId)
