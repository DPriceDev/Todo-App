apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {
    "implementation"(projects.features.auth.ui)
    "implementation"(projects.features.auth.library)
}