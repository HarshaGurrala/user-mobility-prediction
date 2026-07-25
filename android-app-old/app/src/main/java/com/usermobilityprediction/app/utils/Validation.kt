package com.usermobilityprediction.app.utils

object Validation {
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 4
    }

    fun isFullNameValid(name: String): Boolean {
        return name.trim().length >= 2
    }

    fun isPhoneValid(phone: String?): Boolean {
        if (phone == null || phone.isBlank()) return true
        return phone.filter { it.isDigit() }.length >= 7
    }
}
