package com.ufms.nes.features.form.data

import com.ufms.nes.features.form.data.model.FormResponseDto
import com.ufms.nes.features.form.data.remote.FormRemoteDataSource
import javax.inject.Inject

interface FormApi {
    suspend fun getForms(apiKey: String, pageNumber: Int)
}

class FormRemoteDataSourceImpl @Inject constructor(
    private val api: FormApi
) : FormRemoteDataSource {

    override suspend fun getForms(
        apiKey: String,
        pageNumber: Int
    ): ResponseDto<List<FormResponseDto>> {
        return api.getForms(apiKey = apiKey, pageNumber = pageNumber)
    }

}