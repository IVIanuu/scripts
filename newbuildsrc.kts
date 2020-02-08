#!/usr/bin/env kscript
@file:DependsOn("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.41")
@file:Include("utils.kt")

val packageName = args[0]

forward("buildSrc")

// .gitignore
file(".gitignore", """
    /build
    """.trimIndent()
)

// build.gradle.kts
file("build.gradle.kts", """
    repositories {
        jcenter()
    }

    plugins {
        `kotlin-dsl`
    }
    """.trimIndent()
)

// deps
file("src/main/kotlin.dependencies.kt", """
    object Build {
        const val applicationId = "$packageName.sample"
        const val buildToolsVersion = "28.0.3"

        const val compileSdk = 28
        const val minSdk = 23
        const val targetSdk = 28
        const val targetSdkSample = 29
        const val versionCode = 1
        const val versionName = "0.0.1"
    }

    object Publishing {
        const val groupId = "$packageName"
        const val vcsUrl = // todo "https://github.com/IVIanuu/compose"
        const val version = "${'$'}{Build.versionName}-dev-1"
    }

    object Versions {
        const val androidGradlePlugin = "3.5.0-rc01"

        const val androidxAppCompat = "1.1.0-rc01"
        const val androidxUi = "1.0.0-alpha01"

        const val autoService = "1.0-rc6"

        const val bintray = "1.8.4"

        const val kotlin = "1.3.41"

        const val mavenGradle = "2.1"
    }

    object Deps {
        const val androidGradlePlugin = "com.android.tools.build:gradle:${'$'}{Versions.androidGradlePlugin}"

        const val androidxAppCompat = "androidx.appcompat:appcompat:${'$'}{Versions.androidxAppCompat}"
        const val androidxUiMaterial = "androidx.ui:ui-material:${'$'}{Versions.androidxUi}"

        const val autoService = "com.google.auto.service:auto-service:${'$'}{Versions.autoService}"

        const val bintrayGradlePlugin =
            "com.jfrog.bintray.gradle:gradle-bintray-plugin:${'$'}{Versions.bintray}"

        const val kotlinCompiler = "org.jetbrains.kotlin:kotlin-compiler:${'$'}{Versions.kotlin}"
        const val kotlinCompilerEmbeddable = "org.jetbrains.kotlin:kotlin-compiler-embeddable:${'$'}{Versions.kotlin}"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${'$'}{Versions.kotlin}"
        const val kotlinGradlePluginApi = "org.jetbrains.kotlin:kotlin-gradle-plugin-api:${'$'}{Versions.kotlin}"
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${'$'}{Versions.kotlin}"

        const val mavenGradlePlugin =
            "com.github.dcendents:android-maven-gradle-plugin:${'$'}{Versions.mavenGradle}"
    }
    """.trimIndent()
)