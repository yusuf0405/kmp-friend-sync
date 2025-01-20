plugins {
    alias(libs.plugins.friendsync.library.impl)
    alias(libs.plugins.friendsync.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.core)
            implementation(projects.coreUi)
            implementation(projects.uiComponents)
            implementation(projects.featureModules.chat.chatApi)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.chat.impl"
}
