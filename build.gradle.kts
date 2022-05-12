buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle.plugin)
        classpath(libs.gradle.kotlin)
        classpath(libs.gradle.hilt)
        classpath(libs.gradle.junit5)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        // classpath("com.squareup.sqldelight:gradle-plugin:1.5.0")
        // classpath(kotlin("serialization", version = Version.kotlin))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
