plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.friendsync.android.compose)
    alias(libs.plugins.libres)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.datetime)
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
    namespace = "org.joseph.friendsync.core"
}