package si.kamino.gradle.lokalise.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExportFile(
    @SerialName("project_id")
    val projectId: String,
    @SerialName("bundle_url")
    val bundleUrl: String
)
