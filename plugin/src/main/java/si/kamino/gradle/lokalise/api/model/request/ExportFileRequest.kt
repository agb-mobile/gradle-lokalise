package si.kamino.gradle.lokalise.api.model.request

data class ExportFileRequest(
    val originalFilenames: Boolean = true,
    val format: String = "xml",
    val directoryPrefix: String = "",
    val indentation: String = "default",
    val exportEmptyAs : String = "skip",
    val pluralFormat : String = "generic",
    val includeComments:Boolean = true,
    val replaceBreaks:Boolean = true,
    val filterData : Array<String> = arrayOf("translated, reviewed"),
    val filterLangs: Array<String>? =  null

)
