package com.usermobilityprediction.app.data.model

import com.google.gson.annotations.SerializedName

data class EmergencyContactResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("relationship_type")
    val relationshipType: String?,

    @SerializedName("phone_number")
    val phoneNumber: String?,

    @SerializedName("email")
    val email: String?
)