plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("de.mannodermaus.android-junit5")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    defaultConfig {
        minSdk = 23
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    packagingOptions {
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/licenses/**")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/*")
    }

    testBuildType = "espresso"

    buildTypes {
        getByName("debug") { }
        create("espresso") {
            matchingFallbacks.add("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(projects.core)

    /* Kotlin */
    implementation(libs.bundles.kotlin)

    /* Android */
    implementation(libs.bundles.android)

    /* Hilt */
    implementation(libs.bundles.hilt)
    kapt(libs.hiltCompiler)

    /* Compose */
    implementation(libs.bundles.compose)

    /* Testing */
    testImplementation(libs.bundles.unitTest)
    debugImplementation(libs.fragmentTesting)

    androidTestImplementation(libs.bundles.uiTest)
}