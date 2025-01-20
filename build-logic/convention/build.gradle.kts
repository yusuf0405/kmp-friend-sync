import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "org.joseph.friendsync.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("libraryApi") {
            id = "friendsync.library.api"
            implementationClass = "LibraryApiConventionPlugin"
        }
        register("libraryImpl") {
            id = "friendsync.library.impl"
            implementationClass = "LibraryImplConventionPlugin"
        }
        register("libraryCompose") {
            id = "friendsync.library.compose"
            implementationClass = "LibraryComposeConventionPlugin"
        }
        register("applicationCompose") {
            id = "friendsync.application.compose"
            implementationClass = "ApplicationComposeConventionPlugin"
        }
    }
}