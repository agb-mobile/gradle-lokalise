package si.kamino.gradle.lokalise.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class ApplyTask @Inject constructor() : DefaultTask() {

    @get:InputFiles
    val inputDirectory: DirectoryProperty = project.objects.directoryProperty()

    @get:OutputDirectory
    val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    @TaskAction
    fun download() {
        val outDir = outputDirectory.get().asFile
        val prefix = outDir.relativeTo(project.rootDir)

        project.copy {
            it.from(File(inputDirectory.asFile.get(), prefix.path))
            it.into(outDir)
        }

    }

}