package com.usermobilityprediction.app.data.model

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val phone_number: String? = null,
    val password: String,
    val role: String
)