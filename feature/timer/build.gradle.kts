plugins {
    alias(libs.plugins.feature)
}

android {
    namespace = "com.hyunjine.timer"
}

dependencies {
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.kotlinx.coroutines.rx3)
}