package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.models.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {

    @GET("user/profile")
    suspend fun getProfile(): Response<ProfileResponse>
}