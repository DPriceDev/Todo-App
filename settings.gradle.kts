

rootProject.name = "TodoApp"
include(":app")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version "7.3.0-alpha01"
        id("com.android.library") version "7.3.0-alpha01"
        kotlin("org.jetbrains.kotlin.android") version "1.6.10"
    }
}

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

