apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(projects.features.tasks.data)
}