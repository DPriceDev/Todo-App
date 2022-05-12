apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {
    "implementation"(projects.features.auth.ui)
    "implementation"(projects.features.auth.landing)
    "implementation"(projects.features.auth.signup)
    "implementation"(projects.features.auth.signin)
    "implementation"(projects.features.auth.verify)
    "implementation"(projects.features.auth.forgot)
    "implementation"(projects.features.auth.usecases)
    "implementation"(projects.features.auth.data)
}