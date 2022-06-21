import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath(kotlin("serialization", version = "1.6.21"))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf(
            "-Xuse-k2"
        )
    }
}