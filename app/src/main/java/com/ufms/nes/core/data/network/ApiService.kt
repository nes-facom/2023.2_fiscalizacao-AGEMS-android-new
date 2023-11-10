package com.ufms.nes.core.data.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.ufms.nes.BuildConfig
import com.ufms.nes.features.authentication.data.datastore.LocalService
import com.ufms.nes.features.authentication.data.model.User
import com.ufms.nes.features.authentication.data.model.UserResponse
import com.ufms.nes.features.form.data.model.FormResponseDto
import com.ufms.nes.features.form.data.model.ResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

class ApiService @Inject constructor(
    private val client: HttpClient,
    private val localService: LocalService
) {

    suspend fun loginUser(user: User): UserResponse {
        return client.post("${BuildConfig.BASE_URL}/usuarios/autenticar") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun registerUser(user: User): UserResponse {
        return client.post("${BuildConfig.BASE_URL}/usuarios/cadastro") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun refreshToken(): UserResponse {
        val bearerToken = localService.getBearerToken()
        return client.post("${BuildConfig.BASE_URL}/usuarios/renovar-token") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    suspend fun getModels(): List<ModelDTO> {
        val bearerToken = localService.getBearerToken()
        return client.get("${BuildConfig.BASE_URL}/modelo/todos") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getForms(currentPage: Int, pageSize: Int): ResponseDto<List<FormResponseDto>> {
        val bearerToken = localService.getBearerToken()

        val response = client.get("${BuildConfig.BASE_URL}/form/todos/?pagina=${currentPage}&quantidade=${pageSize}") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }
        @Serializable
        data class Formulario(
            @SerialName("pagina")
            val pagina: Int,

            @SerialName("paginaMax")
            val paginaMax: Int,

            @SerialName("formularios")
            val formularios: List<FormResponseDto>
        )

        val body = response.body<Formulario>()
        var res = ResponseDto<List<FormResponseDto>>()

        res.results = body.formularios
        res.page = currentPage
        res.totalPages = body.paginaMax
        return res
//        var res = ResponseDto<List<FormResponseDto>>()
//        val items = (1..35).map {
//            FormResponseDto(
//                id = it,
//                user = "User $it",
//                model = "Model $it",
//                creationDate = LocalDateTime.now(),
//                unit = "Unit $it"
//            )
//        }.toList()
//
//        val chunkedItems = items.chunked<FormResponseDto>(pageSize)
//
//        if (currentPage >= chunkedItems.size) return res
//
//        res.results = chunkedItems[currentPage]
//        res.page = currentPage
//        res.totalPages = chunkedItems.size
//        res.totalResults = items.size
//
//        return res
    }

}
