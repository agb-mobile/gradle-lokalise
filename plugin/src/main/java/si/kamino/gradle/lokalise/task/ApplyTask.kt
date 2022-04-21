package si.kamino.gradle.lokalise.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File
import javax.inject.Inject

@DisableCachingByDefault
abstract class ApplyTask @Inject constructor(
    private val rootDir: File
) : DefaultTask() {

    @get:InputDirectory
    abstract val inputDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Inject
    abstract val fs: FileSystemOperations

    init {
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun download() {
        val outDir = outputDirectory.get().asFile
        val prefix = outDir.relativeTo(rootDir)
        fs.copy {
            it.from(File(inputDirectory.asFile.get(), prefix.path))
            it.into(outDir)
        }
    }

}
