package com.ufms.nes.core.commons

sealed class Resource<T>(
    open val data: T?,
    open val error: String? = null
) {

    data class Success<T>(
        override val data: T?
    ) : Resource<T>(data, null)

    data class Error<T>(
        override val data: T?,
        override val error: String
    ) : Resource<T>(data, error)
}
