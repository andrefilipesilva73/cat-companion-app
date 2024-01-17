// Plugins block specifying the plugins applied to the project
plugins {
    id("com.android.application") // Apply the Android application plugin
    id("org.jetbrains.kotlin.android") // Apply the Kotlin Android plugin
}

// Android block containing configuration for the Android build
android {
    namespace = "etecode.asvaidosas.catcompanion" // Set the namespace for the app
    compileSdk = 33 // Set the Android SDK version to compile against

    // Default configuration for the app
    defaultConfig {
        applicationId = "etecode.asvaidosas.catcompanion" // Set the application ID
        minSdk = 23 // Set the minimum supported SDK version
        targetSdk = 33 // Set the target SDK version
        versionCode = 1 // Set the version code
        versionName = "1.0" // Set the version name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Set the test instrumentation runner
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
        viewBinding = true // Enable View Binding
    }
}

// Dependencies block specifying the project dependencies
dependencies {
    // Implementation dependencies for various libraries
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Test implementation dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
