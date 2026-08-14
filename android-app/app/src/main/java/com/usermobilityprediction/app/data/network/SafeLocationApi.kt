package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.model.SafeLocationCreateRequest
import com.usermobilityprediction.app.data.model.SafeLocationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.usermobilityprediction.app.data.model.NotificationResponse

interface SafeLocationApi {

    // =====================================================
    // GET SAFE LOCATIONS
    // =====================================================

    @GET("safe-location/{user_id}")
    suspend fun getSafeLocations(
        @Path("user_id")
        userId: Int
    ): Response<List<SafeLocationResponse>>


    // =====================================================
    // ADD SAFE LOCATION
    // =====================================================

    @POST("safe-location/guardian/{user_id}")
    suspend fun addSafeLocation(
        @Path("user_id")
        userId: Int,

        @Body
        request: SafeLocationCreateRequest

    ): Response<SafeLocationResponse>


    // =====================================================
    // UPDATE SAFE LOCATION
    // =====================================================

    @PUT("safe-location/{location_id}")
    suspend fun updateSafeLocation(

        @Path("location_id")
        locationId: Int,

        @Body
        request: SafeLocationCreateRequest

    ): Response<SafeLocationResponse>


    // =====================================================
    // DELETE SAFE LOCATION
    // =====================================================

    @DELETE("safe-location/{location_id}")
    suspend fun deleteSafeLocation(

        @Path("location_id")
        locationId: Int

    ): Response<Unit>


    // =====================================================
    // GET SINGLE SAFE LOCATION
    // =====================================================

    @GET("safe-location/detail/{location_id}")
    suspend fun getSafeLocationById(

        @Path("location_id")
        locationId: Int

    ): Response<SafeLocationResponse>


//    @GET("notifications/")
//    suspend fun getNotifications(): Response<List<NotificationResponse>>
//
//
//    @PUT("notifications/{notificationId}/read")
//    suspend fun markNotificationRead(
//        @Path("notificationId") notificationId: Int
//    ): Response<Map<String, Any>>





}