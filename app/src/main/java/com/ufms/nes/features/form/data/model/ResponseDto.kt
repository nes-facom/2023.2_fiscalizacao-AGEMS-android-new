package com.ufms.nes.features.form.data.model

import kotlinx.serialization.SerialName

class ResponseDto<T : Any?> {
    @SerialName("results")
    var results: T? = null

    @SerialName("page")
    var page: Int? = null

    @SerialName("total_pages")
    var totalPages: Int? = null

    @SerialName("total_results")
    var totalResults: Int? = null
}