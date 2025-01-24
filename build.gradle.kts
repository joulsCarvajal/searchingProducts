// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository

    }
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44")

    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.8.0" apply false
}