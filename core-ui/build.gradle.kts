plugins {
    id("com.android.library")
    alias(libs.plugins.compose)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.libres)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":ui-components"))

            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.composeIcons.featherIcons)

            implementation(libs.voyager.navigator)
            implementation(libs.kotlinx.datetime)

            implementation(libs.libres)
            implementation(libs.composeImageLoader)
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.core.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

libres {
    generatedClassName = "MainRes"
    generateNamedArguments = true
    baseLocaleLanguageCode = "en"
    camelCaseNamesForAppleFramework = false
}