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

            implementation(projects.featureModules.addPost.addPostApi)
            
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.composeIcons.featherIcons)
            implementation("com.darkrockstudios:mpfilepicker:3.1.0")
            
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.composeImageLoader)

            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.add.post.impl"
}
