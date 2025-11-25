plugins {
    alias(libs.plugins.feature)
}

android {
    namespace = "com.hyunjine.clothes"
}

dependencies {
    implementation(libs.androidx.material.icons.core)
    implementation(libs.velo.core)
    implementation(libs.velo.android)
}