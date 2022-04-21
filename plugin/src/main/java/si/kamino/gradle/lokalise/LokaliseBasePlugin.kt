package si.kamino.gradle.lokalise

import org.gradle.api.Plugin
import org.gradle.api.Project
import si.kamino.gradle.lokalise.extension.LokaliseBaseExtensions
import si.kamino.gradle.lokalise.task.DownloadTask
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import si.kamino.gradle.lokalise.task.ExtractTask


class LokaliseBasePlugin : Plugin<Project> {

    lateinit var extractTask: TaskProvider<ExtractTask>
    lateinit var syncTask: TaskProvider<Task>

    override fun apply(project: Project) {
        val extension = project.extensions.create("lokalise", LokaliseBaseExtensions::class.java)

        val downloadTask = project.tasks.register("lokaliseDownload", DownloadTask::class.java, extension)
            .apply {
                configure {
                    it.outputFile.set(project.layout.buildDirectory.file("lokalise/export.zip"))
                }
            }

        extractTask = project.tasks.register("lokaliseExtract", ExtractTask::class.java)
            .apply {
                configure {
                    it.dependsOn(downloadTask)
                    it.inputFile.set(downloadTask.flatMap { it.outputFile })
                    it.outputDirectory.set(project.layout.buildDirectory.dir("lokalise/extract"))
                }
            }

        syncTask = project.tasks.register("lokalisePull")
    }
}
