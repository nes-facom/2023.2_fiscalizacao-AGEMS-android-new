package com.ufms.nes.core.commons

inline fun <T> APIResult<T>.verifyResponse(
    onSuccess: (T) -> Unit,
    onError: () -> Unit
) {
    when (this) {
        is APIResult.Success -> onSuccess(this.data)
        is APIResult.Error -> onError()
    }
}
