package com.ufms.nes.core.commons

object Validators {

    fun isPasswordValid(password: String?): Boolean {
        if (password.isNullOrBlank()) {
            return false
        }

        val minLength = 8
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { it.isLetterOrDigit().not() }

        return password.length >= minLength && hasUppercase && hasLowercase && hasDigit && hasSpecialChar
    }
}
