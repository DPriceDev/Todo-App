plugins {
    id("com.android.library")
    id("kotlin-android")
}

apply {
    from("$rootDir/android-feature-build.gradle")
}

android {
    namespace = "dev.dprice.productivity.todo.features.settings.data"
}
