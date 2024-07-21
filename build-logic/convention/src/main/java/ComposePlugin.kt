import com.android.build.gradle.LibraryExtension
import com.example.convention.configure.composeConfigure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {


            extensions.configure<LibraryExtension> {
                composeConfigure(this)
            }
        }
    }
}