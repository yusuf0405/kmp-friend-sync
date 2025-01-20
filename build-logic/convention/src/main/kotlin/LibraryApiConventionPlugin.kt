import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.joseph.friendsync.configureAndroidLibrary
import org.joseph.friendsync.configureLibraryKotlinMultiplatform
import org.joseph.friendsync.libs

class LibraryApiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("android-library").get().get().pluginId)
            apply(libs.findPlugin("kotlin-multiplatform").get().get().pluginId)
            apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension>(::configureLibraryKotlinMultiplatform)
        extensions.configure<LibraryExtension>(::configureAndroidLibrary)
    }
}