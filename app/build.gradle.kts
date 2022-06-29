plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "dev.dprice.productivity.todo"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0.0"
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
        getByName("debug") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        create("espresso") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks.add("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("env")
    productFlavors {
        create("local") {
            dimension = "env"
            applicationIdSuffix = ".local"
            versionNameSuffix = "-local"
        }
        create("integration") {
            dimension = "env"
            applicationIdSuffix = ".int"
            versionNameSuffix = "-int"
        }
        create("prod") {
            dimension = "env"
        }
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            val flavourMap = variantBuilder.productFlavors.toMap()
            when {
                variantBuilder.buildType == "debug" && flavourMap.containsValue("prod") ||
                variantBuilder.buildType == "espresso" && flavourMap.containsValue("prod") ||
                variantBuilder.buildType == "espresso" && flavourMap.containsValue("integration") ||
                variantBuilder.buildType == "release" && flavourMap.containsValue("local") ||
                variantBuilder.buildType == "release" && flavourMap.containsValue("integration")
                -> variantBuilder.enabled = false
                else -> variantBuilder.enabled = true
            }
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(projects.ui)
    implementation(projects.platform)
    implementation(projects.features.tasks.feature)
    implementation(projects.features.tasks.dataImpl)
    implementation(projects.features.settings.feature)
    implementation(projects.features.auth.feature)
    implementation(projects.features.auth.data)
    implementation(projects.core)
    androidTestImplementation(projects.ui.test)


    /* Kotlin */
    implementation(libs.bundles.kotlin)

    /* Android */
    implementation(libs.bundles.android)
    implementation(libs.splashScreen)

    /* Compose */
    implementation(libs.bundles.compose)

    /* Hilt */
    implementation(libs.bundles.hilt)
    kapt(libs.hiltCompiler)

    implementation(libs.bundles.aws)

    /* Logging */
    implementation(libs.timber)

    /* Testing */
    testImplementation(libs.bundles.unitTest)
    debugImplementation(libs.fragmentTesting)

    androidTestImplementation(libs.bundles.uiTest)
}