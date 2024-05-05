plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
    alias(libs.plugins.libres)
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

            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)

            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
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
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        applicationId = "org.joseph.friendsync.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }

    packaging.resources {
        pickFirsts += "/META-INF/LICENSE.md"
        pickFirsts += "/META-INF/LICENSE-notice.md"
        pickFirsts += "/META-INF/AL2.0"
        pickFirsts += "/META-INF/LGPL2.1"
        pickFirsts += "META-INF/versions/9/previous-compilation-data.bin"
    }

    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

compose {
    val kotlinVersion = libs.versions.kotlin.get()
    kotlinCompilerPlugin = "org.jetbrains.kotlin:kotlin-compose-compiler-plugin-embeddable:$kotlinVersion"
}

libres {
    generatedClassName = "MainRes"
    generateNamedArguments = true
    baseLocaleLanguageCode = "en"
    camelCaseNamesForAppleFramework = false
}