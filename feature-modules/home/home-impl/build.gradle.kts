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

            implementation(projects.featureModules.home.homeApi)
            implementation(projects.featureModules.post.postApi)
            implementation(projects.featureModules.profile.profileApi)

            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)
            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.home.impl"
}
