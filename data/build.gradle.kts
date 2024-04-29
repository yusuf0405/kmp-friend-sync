import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.napier)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.multiplatform.settings.serialization)
            implementation(libs.multiplatform.settings.no.arg)
            api(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(libs.koin.android)
            implementation(libs.ktor.client.android)
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.joseph.friendsync.database")
        }
    }
    linkSqlite = true
}

android {
    namespace = "org.joseph.friendsync.data"
}
buildkonfig {
    packageName = "org.joseph.friendsync.data"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "CURRENT_CONFIG",
            value = properties["current_config"].toString()
        )
    }
}