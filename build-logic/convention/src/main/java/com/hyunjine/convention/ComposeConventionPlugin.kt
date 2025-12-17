package com.hyunjine.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * compose 라이브러리를 추가하는 플러그인입니다.
 */
internal class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")

                androidModule {
                    buildFeatures {
                        compose = true
                    }

//                    androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
//                    androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
//                    androidx-activity-compose = { group = "androidx.activity", name = "activity-compose" }
//                    androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
//                    androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
//                    androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
//                    androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
//                    androidx-material-icons-core = { module = "androidx.compose.material:material-icons-core", version.ref = "materialIcon" }
                    dependencies {
                        val composeBom = platform(libs.findLibrary("androidx-compose-bom").get())
                        val material3 = libs.findLibrary("androidx-material3")
                        val activity = libs.findLibrary("androidx.activity.compose")
                        val uiTooling = libs.findLibrary("androidx.ui.tooling")
                        val uiToolingPreview = libs.findLibrary("androidx.ui.tooling.preview")
                        val uiTestManifest = libs.findLibrary("androidx.ui.test.manifest")
                        val uiTestJunit4 = libs.findLibrary("androidx.ui.test.junit4")
                        val materialIconsCore = libs.findLibrary("androidx.material.icons.core")
                        val viewModel = libs.findLibrary("lifecycle.viewmodel.compose")
                        val hiltNavigation = libs.findLibrary("hilt.navigation")

                        implementation(composeBom)
                        implementation(material3)
                        androidTestImplementation(material3)
                        implementation(activity)
                        implementation(uiToolingPreview)
                        debugImplementation(uiTooling)
                        androidTestImplementation(uiTestJunit4)
                        debugImplementation(uiTestManifest)
                        implementation(materialIconsCore)
                        implementation(viewModel)
                        implementation(hiltNavigation)
                    }
                }
            }
        }
    }
}