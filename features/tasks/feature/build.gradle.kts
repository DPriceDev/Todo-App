apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {
    "implementation"(projects.features.groups.api)
    "implementation"(projects.features.tasks.usecase)
    "implementation"(projects.features.groups.usecase)
    "implementation"(projects.features.groups.data)
    "implementation"(projects.features.tasks.data)
}