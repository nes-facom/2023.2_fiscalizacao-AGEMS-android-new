package com.ufms.nes.core.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.ufms.nes.BuildConfig
import com.ufms.nes.data.network.model.request.AddConsumeUnitDTO
import com.ufms.nes.data.network.model.request.AddModelDTO
import com.ufms.nes.data.network.model.response.AddModelResponseDTO
import com.ufms.nes.data.network.model.response.ConsumeUnitItemResponseDTO
import com.ufms.nes.data.network.model.response.ModelResponseDTO
import com.ufms.nes.data.network.model.response.ModelsResponseDTO
import com.ufms.nes.domain.LocalService
import com.ufms.nes.data.network.model.UserDTO
import com.ufms.nes.data.network.model.request.AddFormDTO
import com.ufms.nes.data.network.model.response.AddFormResponseDTO
import com.ufms.nes.data.network.model.response.UserResponse
import com.ufms.nes.data.network.model.response.FormResponseDto
import com.ufms.nes.data.network.model.response.ResponseFormDto
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
import java.util.UUID
import javax.inject.Inject

class ApiService @Inject constructor(
    private val client: HttpClient,
    private val localService: LocalService
) {

    suspend fun loginUser(user: UserDTO): UserResponse {
        return client.post("${BuildConfig.BASE_URL}/usuarios/autenticar") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun registerUser(user: UserDTO): UserResponse {
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

    suspend fun getModels(): List<ModelResponseDTO> {
        val bearerToken = localService.getBearerToken()
        return client.get("${BuildConfig.BASE_URL}/modelo/todos") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getForms(currentPage: Int, pageSize: Int): ResponseFormDto<List<FormResponseDto>> {
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
        val res = ResponseFormDto<List<FormResponseDto>>()

        res.results = body.formularios
        res.page = currentPage
        res.totalPages = body.paginaMax
        return res
    }

    suspend fun getModelsObjects(): ModelsResponseDTO {
        val bearerToken = localService.getBearerToken()
        return client.get("${BuildConfig.BASE_URL}/modelo/") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    suspend fun registerModel(model: AddModelDTO): AddModelResponseDTO {
        val bearerToken = localService.getBearerToken()
        return client.post("${BuildConfig.BASE_URL}/modelo/add") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
            setBody(model)
        }.body()
    }

    suspend fun registerForm(form: AddFormDTO): AddFormResponseDTO {
        val bearerToken = localService.getBearerToken()
        return client.post("${BuildConfig.BASE_URL}/form/add") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
            setBody(form)
        }.body()
    }

    suspend fun getModelById(modelId: UUID): AddModelResponseDTO {
        val bearerToken = localService.getBearerToken()
        return client.get("${BuildConfig.BASE_URL}/modelo/$modelId") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    suspend fun getConsumeUnits(): List<ConsumeUnitItemResponseDTO> {
        val bearerToken = localService.getBearerToken()
        return client.get("${BuildConfig.BASE_URL}/unidade/todas") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
        }.body()
    }

    suspend fun registerConsumeUnit(unit: AddConsumeUnitDTO): ConsumeUnitItemResponseDTO {
        val bearerToken = localService.getBearerToken()
        return client.post("${BuildConfig.BASE_URL}/unidade/add") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
            setBody(unit)
        }.body()
    }
}
