package com.usermobilityprediction.app.data.model

data class NotificationResponse(

    val id: Int,

    val notification_type: String,

    val title: String,

    val message: String,

    val status: String,

    val created_at: String?
)