plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0"
    id("com.google.gms.google-services")
}

allOpen {
    annotation("com.example.searchingproducts.testing.OpenClass")
}

android {
    namespace = "com.example.searchingproducts"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.searchingproducts"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures{
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.gridlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.2.1")

    // JUnit
    testImplementation ("junit:junit:4.13.2")

    // AndroidX Test - Core testing libraries
    testImplementation ("androidx.arch.core:core-testing:2.1.0")

    // Kotlin coroutine testing
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Mockito-Kotlin
    testImplementation ("org.mockito:mockito-core:3.12.4")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.8.0")
    implementation ("androidx.navigation:navigation-ui-ktx:2.8.0")

    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Testing for hilt
    testImplementation(libs.test.implementation)
    kaptTest(libs.hilt.compiler)
    androidTestImplementation(libs.test.implementation)
    kaptAndroidTest(libs.hilt.compiler)

    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.11.0")

    // Glide for images
    implementation(libs.bumptech.glide)

    implementation ("com.airbnb.android:lottie:6.1.0")

    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))

    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.6.1")

    androidTestImplementation ("androidx.test.uiautomator:uiautomator:2.2.0")

    androidTestImplementation ("androidx.test:rules:1.5.0")
}

kapt {
    correctErrorTypes = true
    useBuildCache = false
}