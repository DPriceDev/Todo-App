apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {
    "implementation"(projects.features.tasks.usecase)
    "implementation"(projects.features.tasks.data)
}