plugins {
    alias(libs.plugins.feature)
}

android {
    namespace = "com.hyunjine.clothes"
}

dependencies {
    implementation(libs.velo.core)
    implementation(libs.velo.android)
}