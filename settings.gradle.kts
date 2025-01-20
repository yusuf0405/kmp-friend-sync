pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://plugins.gradle.org/m2/")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "kmp-friend-sync"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":composeApp",
    ":domain",
    ":data",
    ":core",
    ":core-ui",
    ":ui-components",
    ":feature-modules:auth:auth-api",
    ":feature-modules:auth:auth-impl",
    ":feature-modules:home:home-api",
    ":feature-modules:home:home-impl",
    ":feature-modules:chat:chat-api",
    ":feature-modules:chat:chat-impl",
    ":feature-modules:search:search-api",
    ":feature-modules:search:search-impl",
    ":feature-modules:add-post:add-post-api",
    ":feature-modules:add-post:add-post-impl",
    ":feature-modules:notification:notification-api",
    ":feature-modules:notification:notification-impl",
    ":feature-modules:profile:profile-api",
    ":feature-modules:profile:profile-impl",
    ":feature-modules:post:post-api",
    ":feature-modules:post:post-impl"
)