pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "kmp-friend-sync"
include(":composeApp")
include(":domain")
include(":data")

include(":core-ui")
include(":ui-components")

include(":feature-modules:auth:auth-api")
include(":feature-modules:auth:auth-impl")

include(":feature-modules:home:home-api")
include(":feature-modules:home:home-impl")

include(":feature-modules:chat:chat-api")
include(":feature-modules:chat:chat-impl")

include(":feature-modules:search:search-api")
include(":feature-modules:search:search-impl")

include(":feature-modules:add-post:add-post-api")
include(":feature-modules:add-post:add-post-impl")

include(":feature-modules:notification:notification-api")
include(":feature-modules:notification:notification-impl")

include(":feature-modules:profile:profile-api")
include(":feature-modules:profile:profile-impl")

include(":feature-modules:post:post-api")
include(":feature-modules:post:post-impl")