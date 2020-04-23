package si.kamino.gradle.lokalise.task

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import si.kamino.gradle.lokalise.api.LokaliseService
import si.kamino.gradle.lokalise.api.model.request.ExportFileRequest
import si.kamino.gradle.lokalise.extension.LokaliseBaseExtensions
import javax.inject.Inject

abstract class DownloadTask @Inject constructor(
    private val apiService: LokaliseService,
    private val baseExtension: LokaliseBaseExtensions
) : DefaultTask() {

    @get:OutputFile
    val outputFile: RegularFileProperty = project.objects.fileProperty()

    init {
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun download() {
        runBlocking {
            val exportFiles = apiService.exportFiles(
                baseExtension.token!!, baseExtension.id!!,
                ExportFileRequest(indentation = baseExtension.indentation,
                    exportEmptyAs = baseExtension.exportEmptyAs,
                    pluralFormat = baseExtension.pluralFormat,
                    includeComments = baseExtension.includeComments,
                    replaceBreaks = baseExtension.replaceBreaks,
                    filterData = baseExtension.filterData,
                    filterLangs = baseExtension.filterLangs
                )
            ).await()
            val fileResponse = apiService.downloadFile(exportFiles.bundleUrl).await()
            val byteStream = fileResponse.body()!!.byteStream()

            byteStream.use {
                it.copyTo(outputFile.asFile.get().outputStream())
            }
        }
    }

}
