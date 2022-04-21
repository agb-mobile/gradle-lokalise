package si.kamino.gradle.lokalise.task

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import retrofit2.HttpException
import retrofit2.Retrofit
import si.kamino.gradle.lokalise.api.LokaliseService
import si.kamino.gradle.lokalise.api.model.request.ExportFileRequest
import si.kamino.gradle.lokalise.extension.LokaliseBaseExtensions
import javax.inject.Inject

@DisableCachingByDefault
abstract class DownloadTask @Inject constructor(
    private val baseExtension: LokaliseBaseExtensions
) : DefaultTask() {

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    init {
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun download() {
        try {
            runBlocking {
                val apiService = initApiService()
                val exportFiles = apiService.exportFiles(
                    baseExtension.token!!, baseExtension.id!!,
                    ExportFileRequest(
                        indentation = baseExtension.indentation,
                        exportEmptyAs = baseExtension.exportEmptyAs,
                        pluralFormat = baseExtension.pluralFormat,
                        includeComments = baseExtension.includeComments,
                        replaceBreaks = baseExtension.replaceBreaks,
                        filterData = baseExtension.filterData,
                        filterLangs = baseExtension.filterLangs,
                        directoryPrefix = baseExtension.directoryPrefix
                    )
                )
                val fileResponse = apiService.downloadFile(exportFiles.bundleUrl)
                val byteStream = fileResponse.body()!!.byteStream()

                byteStream.use {
                    it.copyTo(outputFile.asFile.get().outputStream())
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val responseStr = e.response()?.body()?.toString() ?: e.response()?.errorBody()?.string()
                println("HTTP Error: Code ${e.code()} message='${e.message()}' ${e.response()}")
                responseStr?.let { println("\tresponse message: $it") }
                e.cause?.let { println("\tcause $it") }
            } else {
                println(e.message)
            }
            throw e
        }
    }

    private fun initApiService(): LokaliseService {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        // Note: To see the logs, you need to run gradle with at least `--info` logging level.
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.lokalise.co/api2/projects/")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()

        return retrofit.create(LokaliseService::class.java)
    }
}
