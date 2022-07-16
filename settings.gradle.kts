include(":app")

include(":ui")
include(":ui:test")
include(":platform")
include(":core")

include(":features:auth:feature")
include(":features:auth:usecases")
include(":features:auth:data")

include(":features:settings:feature")

include(":features:tasks:feature")
include(":features:tasks:usecase")
include(":features:tasks:data")
include(":features:tasks:dataImpl")

include(":features:groups:feature")
include(":features:groups:usecase")
include(":features:groups:data")
include(":features:groups:dataImpl")

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
