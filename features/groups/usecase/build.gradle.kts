apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(projects.features.groups.data)
}