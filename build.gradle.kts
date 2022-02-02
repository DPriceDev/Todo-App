buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
        //classpath("com.squareup.sqldelight:gradle-plugin:1.5.0")
        //classpath(kotlin("serialization", version = Version.kotlin))
    }
}

tasks.register("clean",Delete::class) {
    delete(rootProject.buildDir)
}