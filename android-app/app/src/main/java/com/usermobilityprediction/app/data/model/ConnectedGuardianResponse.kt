package com.usermobilityprediction.app.data.model

data class ConnectedGuardianResponse(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String?,
    val safe_path_id: String?,
    val status: String
)