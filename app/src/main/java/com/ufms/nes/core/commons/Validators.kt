package com.ufms.nes.core.commons

object Validators {

    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

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
    fun isEmailValid(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }
}
