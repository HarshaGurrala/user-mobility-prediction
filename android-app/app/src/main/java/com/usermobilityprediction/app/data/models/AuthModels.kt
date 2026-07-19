package com.usermobilityprediction.app.data.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val phone_number: String? = null,
    val password: String,
    val role: String = "USER"
)

data class UserModel(
    val id: Int,
    val full_name: String,
    val email: String,
    val phone_number: String?,
    val role: String,
    val safe_path_id: String
)

data class LoginResponse(
    val access_token: String,
    val token_type: String,
    val user: UserModel
)
