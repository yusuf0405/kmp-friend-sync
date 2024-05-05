import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.friendsync.android.library)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.skie)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.core)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.napier)
            implementation(libs.skie.annotations)
            implementation(libs.room.runtime)
            implementation(libs.okio)
            implementation(libs.kotlinx.datetime)

            api(libs.datastore.preferences.core)
            api(libs.koin.core)
            api(libs.datastore.core.okio)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(libs.koin.android)
            implementation(libs.ktor.client.android)
            implementation(libs.room.paging)
            implementation(libs.sqlite.bundled)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

val kspConfigurations = listOf(
    "kspAndroid",
    "kspIosSimulatorArm64",
    "kspIosX64",
    "kspIosArm64"
)

dependencies {
    kspConfigurations.forEach { configuration ->
        add(configuration, libs.room.compiler)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
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