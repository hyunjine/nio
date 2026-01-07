import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)

    alias(libs.plugins.bundle.compose)
    alias(libs.plugins.bundle.hilt)
    alias(libs.plugins.bundle.serialization)
}

android {
    namespace = "com.hyunjine.nio"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.hyunjine.nio"
        minSdk = 28
        targetSdk = 36

        val major = 1 ; val minor = 3 ; val patch = 0

        versionName = "${major}.${minor}.${patch}"
        versionCode = 4

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.firebase.crashlytics)
    testImplementation(libs.junit)
    implementation(projects.common)
    implementation(projects.data.nio)
    implementation(projects.feature.clothes)
    implementation(projects.feature.dDay)
    implementation(projects.feature.focus)
    implementation(projects.feature.timer)

    implementation(libs.navigation)
}