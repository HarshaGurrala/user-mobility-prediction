package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.models.LoginRequest
import com.usermobilityprediction.app.data.models.LoginResponse
import com.usermobilityprediction.app.data.models.RegisterRequest
import com.usermobilityprediction.app.data.models.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<UserModel>
}
