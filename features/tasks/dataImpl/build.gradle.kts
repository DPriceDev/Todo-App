apply {
    from("$rootDir/android-library-build.gradle")
}

plugins {
    id("com.squareup.sqldelight")
}

sqldelight {
    database("TasksDatabase") {
        packageName = "dev.dprice.productivity.todo.features.tasks.dataimpl"
    }
}

dependencies {
    "implementation"(projects.features.tasks.data)
    
    /* Database */
    "implementation"("com.squareup.sqldelight:android-driver:1.5.3")
    "implementation"("com.squareup.sqldelight:coroutines-extensions-jvm:1.5.3")
}