package com.usermobilityprediction.app.data.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone_number")
    val phoneNumber: String?,

    @SerializedName("password")
    val password: String,

    @SerializedName("role")
    val role: String = "USER"
)

data class LoginRequest(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

data class UserDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone_number")
    val phoneNumber: String?,

    @SerializedName("role")
    val role: String,

    @SerializedName("safe_path_id")
    val safePathId: String
)

data class AuthResponse(

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("user")
    val user: UserDto
)