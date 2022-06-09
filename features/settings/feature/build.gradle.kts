plugins {
    id("com.android.library")
    id("kotlin-android")
}

apply {
    from("$rootDir/android-feature-build.gradle")
}

android {
    namespace = "dev.dprice.productivity.todo.features.settings.feature"
}

dependencies {
    "implementation"(projects.features.settings.data)
}