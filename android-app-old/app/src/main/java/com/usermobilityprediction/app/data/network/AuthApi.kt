package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.models.AuthResponse
import com.usermobilityprediction.app.data.models.LoginRequest
import com.usermobilityprediction.app.data.models.RegisterRequest
import com.usermobilityprediction.app.data.models.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<UserDto>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @GET("user/profile")
    suspend fun profile(): Response<UserDto>
}