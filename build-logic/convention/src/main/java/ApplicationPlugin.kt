import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.configure.applicationConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("")
                apply("")
                apply("")
            }

            extensions.configure<ApplicationExtension> {
                applicationConfigure(this)
            }
        }
    }
}