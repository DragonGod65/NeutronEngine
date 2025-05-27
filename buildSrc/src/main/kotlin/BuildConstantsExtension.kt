import org.gradle.api.DefaultTask
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.getByType
import org.gradle.api.Project as GradleProject
import java.io.File

public interface BuildConstantsExtension {
    public val properties: MapProperty<String, String>
}

internal fun GradleProject.buildConstantDir(): File = layout.buildDirectory
    .dir("generated/compile")
    .get()
    .asFile

public abstract class GenerateBuildConstants : DefaultTask() {
    private val ext = project.extensions.getByType<BuildConstantsExtension>()

    @TaskAction
    internal fun execute() {
        val properties = ext.properties.get()
        val content = properties.entries.joinToString("\n") { "\tconst val ${it.key} = \"${it.value}\"" }
        val generatedDir = project.buildConstantDir()

        generatedDir.mkdirs()

        val buildConstantsFile = generatedDir.resolve(relative = "BuildConstants.kt")
        if (!buildConstantsFile.exists()) {
            buildConstantsFile.createNewFile()
        }

        buildConstantsFile.writeText(
            """
// This file is generated automatically. Do not edit or modify!
package ${Project.GROUP}.build

internal object BuildConstants {
$content
}
            """.trimIndent()
        )
    }
}
