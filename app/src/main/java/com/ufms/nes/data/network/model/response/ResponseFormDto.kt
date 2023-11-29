package com.ufms.nes.data.network.model.response

import kotlinx.serialization.SerialName

class ResponseFormDto<T : Any?> {
    @SerialName("results")
    var results: T? = null

    @SerialName("page")
    var page: Int? = null

    @SerialName("total_pages")
    var totalPages: Int? = null
}