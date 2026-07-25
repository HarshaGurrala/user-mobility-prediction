package com.usermobilityprediction.app.data.model

data class UserUpdateRequest(
    val full_name: String,
    val email: String,
    val phone_number: String?
)