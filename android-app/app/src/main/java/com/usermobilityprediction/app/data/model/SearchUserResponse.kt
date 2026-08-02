package com.usermobilityprediction.app.data.model

data class SearchUserResponse(

    val id: Int,

    val full_name: String,

    val email: String,

    val phone_number: String,

    val safe_path_id: String,

    val is_online: Boolean

)