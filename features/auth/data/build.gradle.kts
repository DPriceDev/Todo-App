apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {
    "implementation"(libs.bundles.aws)
}