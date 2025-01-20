plugins {
    alias(libs.plugins.friendsync.library.api)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.coil.compose.core)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.core"
}