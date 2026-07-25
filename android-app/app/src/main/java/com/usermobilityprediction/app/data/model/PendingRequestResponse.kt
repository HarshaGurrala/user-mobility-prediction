package com.usermobilityprediction.app.data.model

data class PendingRequestResponse(
    val request_id: Int,
    val guardian_id: Int,
    val guardian_name: String,
    val guardian_email: String,
    val status: String
)