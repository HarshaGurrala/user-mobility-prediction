package com.usermobilityprediction.app.data.model

data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)