plugins {
    alias(libs.plugins.friendsync.library.api)
    alias(libs.plugins.friendsync.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.core)

            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)
            implementation(libs.composeIcons.featherIcons)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.ui.components"
}
