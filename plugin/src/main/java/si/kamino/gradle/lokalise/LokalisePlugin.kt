package si.kamino.gradle.lokalise

import org.gradle.api.Plugin
import org.gradle.api.Project
import si.kamino.gradle.lokalise.task.ApplyTask
import java.io.File

class LokalisePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val rootProject = project.rootProject
        val basePlugin: Any

        basePlugin = if (rootProject.plugins.hasPlugin(LokaliseBasePlugin::class.java)) {
            rootProject.plugins.getPlugin(LokaliseBasePlugin::class.java)
        } else {
            project.plugins.apply(LokaliseBasePlugin::class.java)
        }

        val applyTask = project.tasks.register("lokaliseApply", ApplyTask::class.java, project.rootDir)
            .apply {
                configure {
                    it.inputDirectory.set(basePlugin.extractTask.flatMap { it.outputDirectory })
                    it.outputDirectory.set(File(project.projectDir, "src/main/res/"))
                }
            }

        basePlugin.syncTask.configure {
            it.dependsOn.add(applyTask)
        }
    }
/*
    companion object {
        fun isAndroidProject(project: Project): Boolean {
            return project.plugins.hasPlugin("com.android.application")
                    || project.plugins.hasPlugin("com.android.library")
        }

        fun getAndroidExtension(project: Project): BuildProperties {
            return when {
                project.plugins.hasPlugin("com.android.application") -> project.extensions.getByType(AppExtension::class.java)
                project.plugins.hasPlugin("com.android.library") -> project.extensions.getByType(LibraryExtension::class.java)
                else -> throw IllegalStateException("Android plugin not found")
            }
        }

    }*/
}
