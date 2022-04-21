package si.kamino.gradle.lokalise.api.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExportFileRequest(
    @SerialName("original_filenames")
    val originalFilenames: Boolean = true,
    @SerialName("format")
    val format: String = "xml",
    @SerialName("directory_prefix")
    val directoryPrefix: String? = null,
    @SerialName("indentation")
    val indentation: String = "default",
    @SerialName("export_empty_as")
    val exportEmptyAs : String = "skip",
    @SerialName("plural_format")
    val pluralFormat : String = "generic",
    @SerialName("include_comments")
    val includeComments:Boolean = true,
    @SerialName("replace_breaks")
    val replaceBreaks:Boolean = true,
    @SerialName("filter_data")
    val filterData : Array<String> = arrayOf("translated, reviewed"),
    @SerialName("filter_langs")
    val filterLangs: Array<String>? =  null

)
