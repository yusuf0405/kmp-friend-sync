plugins {
    alias(libs.plugins.friendsync.android.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.auth.api"
}