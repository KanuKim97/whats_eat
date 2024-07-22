import com.android.build.gradle.LibraryExtension
import com.example.convention.configure.composeConfigure
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                buildFeatures.compose = true
                composeOptions.kotlinCompilerExtensionVersion = libs.findVersion("compose").get().requiredVersion
            }

            composeConfigure()
        }
    }
}