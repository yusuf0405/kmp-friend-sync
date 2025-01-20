plugins {
    alias(libs.plugins.friendsync.library.impl)
    alias(libs.plugins.friendsync.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.coreUi)
            implementation(projects.domain)
            implementation(projects.uiComponents)
            implementation(projects.featureModules.auth.authApi)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.auth.impl"
}