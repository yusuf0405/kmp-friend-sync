plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.friendsync.android.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)

            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
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
