plugins {
    id("com.android.library")
    id("de.mannodermaus.android-junit5")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
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
        create("espresso") {
            matchingFallbacks.add("debug")
        }
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            val flavourMap = variantBuilder.productFlavors.toMap()
            variantBuilder.enabled = when (variantBuilder.buildType) {
                "debug" -> false
                "release" -> false
                else -> true
            }
        }
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.platform)
    implementation(projects.ui)

    /* Kotlin */
    implementation(libs.bundles.kotlin)

    /* Android */
    implementation(libs.bundles.android)

    /* Compose */
    implementation(libs.bundles.compose)

    /* Hilt */
    implementation(libs.bundles.hilt)

    /* Testing */
    testImplementation(libs.bundles.unitTest)
    debugImplementation(libs.fragmentTesting)

    implementation(libs.bundles.uiTest)

    kapt(libs.hiltKaptTest)
    kaptAndroidTest(libs.hiltKaptTest)

    implementation("androidx.compose.ui:ui-test-manifest:1.2.0-alpha02")
}
