package si.kamino.gradle.lokalise.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import javax.inject.Inject

@DisableCachingByDefault
abstract class ExtractTask @Inject constructor() : DefaultTask() {

    @get:InputFile
    abstract val inputFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Inject
    abstract val fs: FileSystemOperations

    @get:Inject
    abstract val zip: ArchiveOperations

    init {
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun download() {
        fs.delete {
            it.delete(outputDirectory)
        }
        fs.copy {
            it.from(zip.zipTree(inputFile.asFile.get()))
            it.into(outputDirectory)
        }
    }
}
