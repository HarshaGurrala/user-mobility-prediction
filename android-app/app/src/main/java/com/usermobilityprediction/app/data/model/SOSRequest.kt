package com.usermobilityprediction.app.data.model

data class SOSRequest(
    val latitude: Double,
    val longitude: Double,
    val message: String = "Emergency! I need help."
)