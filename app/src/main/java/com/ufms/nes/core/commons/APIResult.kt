package com.ufms.nes.core.commons

/**
 * Can be extended with more fine-granular types.
 */
sealed interface APIResult<T> {
    data class Success<T>(val data: T) : APIResult<T>
    data class Error<T>(val data: T?) : APIResult<T>
}