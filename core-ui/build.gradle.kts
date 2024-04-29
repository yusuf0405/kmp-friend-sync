plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.friendsync.android.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.libres)
}

kotlin {
    sourceSets {
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.uiComponents)

            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)

            implementation(libs.kotlinx.datetime)
            implementation(libs.libres)
            implementation(libs.composeImageLoader)
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
        }
    }
}

android {
    namespace = "org.joseph.friendsync.core.ui"
}

libres {
    generatedClassName = "MainRes"
    generateNamedArguments = true
    baseLocaleLanguageCode = "en"
    camelCaseNamesForAppleFramework = false
}