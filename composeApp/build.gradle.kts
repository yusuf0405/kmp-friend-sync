plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.friendsync.application.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTarget.get()
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            compilation.kotlinOptions.freeCompilerArgs += arrayOf("-linker-options", "-lsqlite3")
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.coreUi)
            implementation(projects.data)
            implementation(projects.domain)
            implementation(projects.uiComponents)

            implementation(projects.featureModules.auth.authApi)
            implementation(projects.featureModules.auth.authImpl)

            implementation(projects.featureModules.home.homeApi)
            implementation(projects.featureModules.home.homeImpl)

            implementation(projects.featureModules.chat.chatApi)
            implementation(projects.featureModules.chat.chatImpl)

            implementation(projects.featureModules.post.postApi)
            implementation(projects.featureModules.post.postImpl)

            implementation(projects.featureModules.profile.profileApi)
            implementation(projects.featureModules.profile.profileImpl)

            implementation(projects.featureModules.search.searchApi)
            implementation(projects.featureModules.search.searchImpl)

            implementation(projects.featureModules.addPost.addPostApi)
            implementation(projects.featureModules.addPost.addPostImpl)

            implementation(libs.composeIcons.featherIcons)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)

            implementation(libs.coil.compose.core)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)

            // Coil
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.compose.uitooling)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.core.splashscreen)
        }
    }
}

android {
    namespace = "org.joseph.friendsync"

    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}

composeCompiler {
    enableStrongSkippingMode = true

    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}