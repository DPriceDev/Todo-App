include(":app")
include(":ui")
include(":platform")
include(":core")
include(":features:tasks")
include(":features:auth:feature")
include(":features:auth:data")

rootProject.name = "TodoApp"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
include(":features:auth:landing")
include(":features:auth:ui")
include(":features:auth:signup")
include(":features:auth:signin")
include(":features:auth:verify")
include(":ui:test")
include(":features:auth:usecases")
include(":features:auth:forgot")
