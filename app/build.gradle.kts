// Plugins block specifying the plugins applied to the project
plugins {
    id("com.android.application") // Apply the Android application plugin
    id("org.jetbrains.kotlin.android") // Apply the Kotlin Android plugin
    id("com.google.devtools.ksp") // Make "Room" work
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
        debug {
            buildConfigField("String", "CAT_API_BASE_URL", "\"https://api.thecatapi.com\"")
            buildConfigField("String", "CAT_API_API_KEY", "\"live_vBJtyckXF5MeP5oY1dO85zBt0aFY74OJKbhyCNrYk3mlcva4Ey9bAm7gCIiYeV1M\"")
        }
        release {
            isMinifyEnabled = false // Disable code shrinking and obfuscation
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            ) // Specify ProGuard configuration files
            buildConfigField("String", "CAT_API_BASE_URL", "\"https://api.thecatapi.com\"")
            buildConfigField("String", "CAT_API_API_KEY", "\"live_vBJtyckXF5MeP5oY1dO85zBt0aFY74OJKbhyCNrYk3mlcva4Ey9bAm7gCIiYeV1M\"")
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
        buildConfig = true
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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Add the Navigation Component dependency
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Retrofit + GSON
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.paging:paging-compose:3.2.1")

    // Images from the internet
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Extended Icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // Room dependencies
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")
    implementation("androidx.navigation:navigation-testing:2.7.6")
    ksp("androidx.room:room-compiler:2.6.1")

    // Test implementation dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
