plugins {
    alias(libs.plugins.friendsync.library.api)
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