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

import com.usermobilityprediction.app.data.model.AnalyticsOverviewResponse
import com.usermobilityprediction.app.data.model.DailyDistanceResponse
import com.usermobilityprediction.app.data.model.WeeklyDistanceResponse
import com.usermobilityprediction.app.data.model.PredictionAnalyticsResponse
import com.usermobilityprediction.app.data.model.SafetyAnalyticsResponse
import com.usermobilityprediction.app.data.model.AlertAnalyticsResponse
import com.usermobilityprediction.app.data.model.SafeZoneAnalyticsResponse
import com.usermobilityprediction.app.data.model.PredictionResponse

import retrofit2.http.Path
import com.usermobilityprediction.app.data.model.SearchUserResponse

import com.usermobilityprediction.app.data.model.PendingRequestResponse
import com.usermobilityprediction.app.data.model.ConnectedGuardianResponse


import com.usermobilityprediction.app.data.model.ResetPasswordRequest
import com.usermobilityprediction.app.data.model.ForgotPasswordRequest
import com.usermobilityprediction.app.data.model.ForgotPasswordResponse

interface ApiService {


    @GET("user-dashboard/me")
    suspend fun getUserDashboard(): Response<UserDashboardResponse>


    @POST("location/update/{user_id}")
    suspend fun uploadLocation(

        @Path("user_id")
        userId: Int,

        @Body
        request: LocationRequest

    ): Response<Map<String, String>>


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

    // ===============================
// USER - Pending Guardian Requests
// ===============================

    @GET("guardian/pending")
    suspend fun getPendingRequests():

            Response<List<PendingRequestResponse>>



// ===============================
// USER accepts guardian request
// ===============================

    @PUT("guardian/accept/{request_id}")
    suspend fun acceptGuardianRequest(

        @Path("request_id")
        requestId: Int

    ): Response<Map<String, String>>



// ===============================
// USER rejects guardian request
// ===============================

    @PUT("guardian/reject/{request_id}")
    suspend fun rejectGuardianRequest(

        @Path("request_id")
        requestId: Int

    ): Response<Map<String, String>>


    @GET("analytics/overview/{user_id}")
    suspend fun getAnalyticsOverview(
        @Path("user_id") userId: Int
    ): Response<AnalyticsOverviewResponse>


    @GET("analytics/daily-distance/{user_id}")
    suspend fun getDailyDistance(
        @Path("user_id") userId: Int
    ): Response<List<DailyDistanceResponse>>


    @GET("analytics/weekly-distance/{user_id}")
    suspend fun getWeeklyDistance(
        @Path("user_id") userId: Int
    ): Response<List<WeeklyDistanceResponse>>


    @GET("analytics/predictions/{user_id}")
    suspend fun getPredictionAnalytics(
        @Path("user_id") userId: Int
    ): Response<PredictionAnalyticsResponse>


    @GET("analytics/safety/{user_id}")
    suspend fun getSafetyAnalytics(
        @Path("user_id") userId: Int
    ): Response<SafetyAnalyticsResponse>


    @GET("analytics/alerts/{user_id}")
    suspend fun getAlertAnalytics(
        @Path("user_id") userId: Int
    ): Response<AlertAnalyticsResponse>


    @GET("analytics/safe-zones/{user_id}")
    suspend fun getSafeZoneAnalytics(
        @Path("user_id") userId: Int
    ): Response<SafeZoneAnalyticsResponse>


    @GET("prediction/next/{user_id}")
    suspend fun getNextPrediction(
        @Path("user_id") userId: Int
    ): Response<PredictionResponse>


    @GET("users/search/{safe_path_id}")
    suspend fun searchUser(

        @Path("safe_path_id")
        safePathId: String

    ): Response<SearchUserResponse>


    @GET("guardian/my-guardians")
    suspend fun getMyGuardians():
            Response<List<ConnectedGuardianResponse>>


    @PUT("auth/reset-password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): Response<Map<String, String>>


    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>

}
