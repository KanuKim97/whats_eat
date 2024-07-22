import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.example.convention.configure.applicationConfigure
import com.example.convention.configure.composeConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
            }

            extensions.configure<BaseAppModuleExtension> {
                applicationConfigure(this)
            }

            composeConfigure()
        }
    }
}