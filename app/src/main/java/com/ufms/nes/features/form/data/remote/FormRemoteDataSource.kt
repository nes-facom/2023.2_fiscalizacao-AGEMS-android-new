package com.ufms.nes.features.form.data.remote

import com.mmj.movieapp.core.generic.dto.ResponseDto
import com.mmj.movieapp.data.model.remote.dto.response.MovieResponseDto
import com.ufms.nes.features.form.data.model.FormResponseDto

interface FormRemoteDataSource {

    suspend fun getForms(
        apiKey: String,
        pageNumber: Int
    ): ResponseDto<List<FormResponseDto>>

}