package com.usermobilityprediction.app.data.model


data class EmergencyContactResponse(

    val id: Int,

    val name: String,

    val relationshipType: String?,

    val phoneNumber: String,

    val email: String?

)