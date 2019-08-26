package si.kamino.gradle.lokalise.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ExtractTask @Inject constructor() : DefaultTask() {

    @get:InputFile
    val inputFile: RegularFileProperty = project.objects.fileProperty()

    @get:OutputDirectory
    val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    @TaskAction
    fun download() {
        project.copy {
            it.from(project.zipTree(inputFile.asFile.get()))
            it.into(outputDirectory.asFile.get())
        }

    }

}