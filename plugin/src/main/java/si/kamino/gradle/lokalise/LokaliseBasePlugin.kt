package si.kamino.gradle.lokalise

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.gradle.api.Plugin
import org.gradle.api.Project
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import si.kamino.gradle.lokalise.api.LokaliseService
import si.kamino.gradle.lokalise.extension.LokaliseBaseExtensions
import si.kamino.gradle.lokalise.task.DownloadTask
import okhttp3.logging.HttpLoggingInterceptor
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import si.kamino.gradle.lokalise.task.ExtractTask


class LokaliseBasePlugin : Plugin<Project> {

    private lateinit var apiService: LokaliseService

    lateinit var extractTask: TaskProvider<ExtractTask>
    lateinit var syncTask: TaskProvider<Task>

    override fun apply(project: Project) {
        initApiService()

        val extension = project.extensions.create("lokalise", LokaliseBaseExtensions::class.java)

        val downloadTask = project.tasks.register("lokaliseDownload", DownloadTask::class.java, apiService, extension)
            .apply {
                configure {
                    it.outputFile.set(project.layout.buildDirectory.file("lokalise/export.zip"))
                }
            }

        extractTask = project.tasks.register("lokaliseExtract", ExtractTask::class.java)
            .apply {
                configure {
                    it.inputFile.set(downloadTask.flatMap { it.outputFile })
                    it.outputDirectory.set(project.layout.buildDirectory.dir("lokalise/extract"))
                }
            }

        syncTask = project.tasks.register("lokalisePull")
    }

    private fun initApiService() {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val converterFactory = GsonConverterFactory.create(gson)

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

        apiService = retrofit.create(LokaliseService::class.java)
    }

}
