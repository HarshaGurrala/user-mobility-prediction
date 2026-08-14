package com.usermobilityprediction.app.data.model

data class UserResponse(
    val id: Int,
    val full_name: String,
    val email: String,
    val phone_number: String,
    val role: String,
    val safe_path_id: String,
    val profile_picture: String? = null
)