package si.kamino.gradle.lokalise.api

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import si.kamino.gradle.lokalise.api.model.request.ExportFileRequest
import si.kamino.gradle.lokalise.api.model.response.ExportFile


interface LokaliseService {

    @POST("{projectId}/files/download")
    fun exportFiles(@Header("X-Api-Token") apiKey: String, @Path("projectId") projectId: String, @Body export: ExportFileRequest): Deferred<ExportFile>

    @GET @Streaming
    fun downloadFile(@Url url: String): Deferred<Response<ResponseBody>>

}