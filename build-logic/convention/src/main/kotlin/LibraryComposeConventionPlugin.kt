import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.joseph.friendsync.libs
import org.joseph.friendsync.configureComposeMultiplatform
import org.joseph.friendsync.configureAndroidLibraryCompose

class LibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("android-library").get().get().pluginId)
            apply(libs.findPlugin("compose-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("compose-compiler").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension>(::configureComposeMultiplatform)
        extensions.configure<LibraryExtension>(::configureAndroidLibraryCompose)
    }
}

