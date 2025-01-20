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
            implementation(projects.featureModules.addPost.addPostApi)
            implementation(libs.mpfilepicker)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.add.post.impl"
}
