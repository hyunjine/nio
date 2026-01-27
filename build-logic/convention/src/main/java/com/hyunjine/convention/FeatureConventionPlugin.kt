package com.hyunjine.convention

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import kotlin.text.set

/**
 * android module에서 구성되는 속성입니다.
 */
class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("compose.plugin")
                apply("hilt.plugin")
                apply("serialization.plugin")
            }
            extensions.configure<LibraryExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 28

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
                extensions.configure<KotlinAndroidProjectExtension> {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                }
                dependencies {
                    implementation(libs.findLibrary("androidx.core.ktx"))
                    implementation(libs.findLibrary("androidx.lifecycle.runtime.ktx"))
                    implementation(libs.findLibrary("lifecycle.viewmodel"))
                    implementation(libs.findLibrary("constraintlayout"))
                    implementation(libs.findLibrary("navigation3.runtime"))
                    implementation(libs.findLibrary("navigation3.viewmodel"))
                    implementation(project(":common"))
                }
            }
        }
    }
}