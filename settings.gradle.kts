include(":app")
include(":ui")
include(":platform")
include(":core")
include(":features:tasks:feature")
include(":features:auth:feature")
include(":features:auth:usecases")
include(":features:auth:data")
include(":ui:test")
include(":features:settings:feature")
include(":features:tasks:usecase")
include(":features:tasks:data")
include(":features:tasks:dataImpl")

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
