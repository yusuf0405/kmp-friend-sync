plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.friendsync.android.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.core)
            implementation(projects.coreUi)
            implementation(projects.uiComponents)
            implementation(projects.featureModules.chat.chatApi)

            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.chat.impl"
}
