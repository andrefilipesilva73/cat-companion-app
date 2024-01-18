// Plugins block specifying the plugins applied to the project
plugins {
    id("com.android.application") // Apply the Android application plugin
    id("org.jetbrains.kotlin.android") // Apply the Kotlin Android plugin
}

// Android block containing configuration for the Android build
android {
    namespace = "com.catcompanion.app" // Set the namespace for the app
    compileSdk = 34 // Set the Android SDK version to compile against

    // Default configuration for the app
    defaultConfig {
        applicationId = "com.catcompanion.app" // Set the application ID
        minSdk = 21 // Set the minimum supported SDK version
        targetSdk = 34 // Set the target SDK version
        versionCode = 1 // Set the version code
        versionName = "1.0" // Set the version name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Set the test instrumentation runner
        vectorDrawables {
            useSupportLibrary = true // Enable support for vector drawables
        }
    }

    // Build types configuration
    buildTypes {
        release {
            isMinifyEnabled = false // Disable code shrinking and obfuscation
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            ) // Specify ProGuard configuration files
        }
    }

    // Compile options configuration
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Set Java source compatibility
        targetCompatibility = JavaVersion.VERSION_1_8 // Set Java target compatibility
    }

    // Kotlin options configuration
    kotlinOptions {
        jvmTarget = "1.8" // Set the JVM target version for Kotlin
    }

    // Build features configuration
    buildFeatures {
        compose = true // Enable Jetpack Compose
    }

    // Compose options configuration
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Set the version of the Compose Kotlin compiler extension
    }

    // Packaging configuration
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // Exclude specific resources from packaging
        }
    }
}

// Dependencies block specifying the project dependencies
dependencies {
    // Implementation dependencies for various libraries
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Add the Navigation Component dependency
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Test implementation dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
