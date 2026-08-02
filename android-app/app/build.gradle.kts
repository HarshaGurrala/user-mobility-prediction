plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.usermobilityprediction.app"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.usermobilityprediction.app"
        minSdk = 27
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
        implementation("androidx.security:security-crypto:1.1.0-alpha06")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
    implementation("com.google.maps.android:maps-compose:6.1.2")
    implementation("com.patrykandpatrick.vico:compose:2.1.3")
    implementation("com.patrykandpatrick.vico:compose-m3:2.1.3")
    implementation("com.patrykandpatrick.vico:core:2.1.3")

    implementation("com.google.android.libraries.places:places:4.1.0")
        // Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.retrofit2:converter-gson:2.11.0")

        // OkHttp
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")


        // AndroidX Security
        implementation("androidx.security:security-crypto:1.1.0-alpha06")
        implementation("com.google.android.gms:play-services-location:21.3.0")
        // Navigation
        implementation("androidx.navigation:navigation-compose:2.9.0")

        // Compose
        implementation("androidx.compose.animation:animation")
        implementation("androidx.compose.material:material-icons-extended")
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)

        // Kotlin Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

        // AndroidX Core & Lifecycle
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)

        // Testing
        testImplementation(libs.junit)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(libs.androidx.junit)

        // Debug
        debugImplementation(libs.androidx.compose.ui.test.manifest)
        debugImplementation(libs.androidx.compose.ui.tooling)
}