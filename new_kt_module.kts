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
        kotlin("jvm")
    }
    apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/java-8.gradle")
    apply(from = "https://raw.githubusercontent.com/IVIanuu/gradle-scripts/master/mvn-publish.gradle")

    dependencies {
        api(Deps.kotlinStdLib)
    }
    """.trimIndent()
)

// src dirs
val packagePath = packageName.replace(".", "/")
ensureDirs("/src/main/kotlin/$packagePath")
ensureDirs("/src/test/kotlin/$packagePath")

// add to settings.gradle.kts
includeModuleInSettingsGradle(moduleId)