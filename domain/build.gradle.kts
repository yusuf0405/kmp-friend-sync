plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.sqldelight)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.kotlinx.datetime)
            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            api(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(libs.koin.android)
            implementation(libs.ktor.client.android)
            implementation(libs.sqldelight.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
    }
}

android {
    namespace = "org.joseph.friendsync"
}