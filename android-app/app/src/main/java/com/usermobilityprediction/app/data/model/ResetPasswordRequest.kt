package com.usermobilityprediction.app.data.model

data class ResetPasswordRequest(
    val token: String,
    val new_password: String
)