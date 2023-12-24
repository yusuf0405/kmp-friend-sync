plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    id("com.android.library")
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    sourceSets {
        commonMain.dependencies {
        }
    }
}

android {
    namespace = "org.joseph.friendsync.search.api"

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}