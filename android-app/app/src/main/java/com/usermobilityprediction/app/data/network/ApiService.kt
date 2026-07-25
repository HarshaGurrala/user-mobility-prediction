package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.model.ChangePasswordRequest

import com.usermobilityprediction.app.data.model.LoginRequest
import com.usermobilityprediction.app.data.model.LoginResponse
import com.usermobilityprediction.app.data.model.RegisterRequest
import com.usermobilityprediction.app.data.model.RegisterResponse
import com.usermobilityprediction.app.data.model.UserResponse
import com.usermobilityprediction.app.data.model.UserUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import com.usermobilityprediction.app.data.model.GuardianRequest
import com.usermobilityprediction.app.data.model.UserDashboardResponse
import com.usermobilityprediction.app.data.model.LocationRequest


import retrofit2.http.Path

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("users/me")
    suspend fun getCurrentUser(): Response<UserResponse>

    @PUT("users/me")
    suspend fun updateCurrentUser(
        @Body request: UserUpdateRequest
    ): Response<UserResponse>

    @POST("auth/change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    ): Response<Map<String, String>>

//    @GET("dashboard/me")
//    suspend fun getDashboard(): Response<DashboardResponse>

    @POST("guardian/connect")
    suspend fun connectGuardian(
        @Body request: GuardianRequest
    ): Response<Map<String, String>>

//    @GET("guardian/pending")
//    suspend fun getPendingRequests():
//            Response<List<PendingRequestResponse>>

//    @PUT("guardian/accept/{request_id}")
//    suspend fun acceptGuardianRequest(
//        @Path("request_id") requestId: Int
//    ): Response<Map<String, String>>

    @PUT("guardian/reject/{request_id}")
    suspend fun rejectGuardianRequest(
        @Path("request_id") requestId: Int
    ): Response<Map<String, String>>


    @GET("user-dashboard/me")
    suspend fun getUserDashboard(): Response<UserDashboardResponse>

    @POST("location/update/{user_id}")
    suspend fun uploadLocation(
        @Path("user_id") userId: Int,
        @Body request: LocationRequest
    ): Response<Map<String, String>>
}
