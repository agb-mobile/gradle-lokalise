package si.kamino.gradle.lokalise.api.model.request

data class ExportFileRequest(
    val originalFilenames: Boolean = true,
    val format: String = "xml",
    val directoryPrefix: String = "",
    val indentation: String = "default"
)