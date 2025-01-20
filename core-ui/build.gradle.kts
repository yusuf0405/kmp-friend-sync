plugins {
    alias(libs.plugins.friendsync.library.impl)
    alias(libs.plugins.friendsync.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.uiComponents)
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.compose.uitooling)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = auto
}

android {
    namespace = "org.joseph.friendsync.core.ui"
}