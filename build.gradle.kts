// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${ Version.kotlin }")
        classpath(Dependencies.Hilt.gradlePlugin)
        //classpath("com.squareup.sqldelight:gradle-plugin:1.5.0")
        //classpath(kotlin("serialization", version = Version.kotlin))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri(Dependencies.Experimental.androidx) }
    }
}

tasks.register("clean",Delete::class) {
    delete(rootProject.buildDir)
}