package si.kamino.gradle.lokalise

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertTrue
import org.junit.Test
import si.kamino.gradle.lokalise.task.DownloadTask
import si.kamino.gradle.lokalise.task.ExtractTask

class LokalisePluginTest {

    @Test
    fun testLokalisePlugin() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("si.kamino.gradle.lokalise")

        assertTrue(project.tasks.getByName("lokaliseDownload") is DownloadTask)
        assertTrue(project.tasks.getByName("lokaliseExtract") is ExtractTask)
    }
}
