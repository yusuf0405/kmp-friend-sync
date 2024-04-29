plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.friendsync.android.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.coreUi)
            implementation(projects.domain)
            implementation(projects.uiComponents)
            implementation(projects.featureModules.auth.authApi)

            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.auth.impl"
}