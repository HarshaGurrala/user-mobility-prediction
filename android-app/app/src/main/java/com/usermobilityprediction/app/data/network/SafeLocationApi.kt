package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.model.SafeLocationCreateRequest
import com.usermobilityprediction.app.data.model.SafeLocationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.DELETE

import retrofit2.http.PUT

interface SafeLocationApi {


    @GET("safe-location/{user_id}")
    suspend fun getSafeLocations(
        @Path("user_id")
        userId: Int
    ): Response<List<SafeLocationResponse>>



    @POST("safe-location/{user_id}")
    suspend fun addSafeLocation(
        @Path("user_id")
        userId: Int,

        @Body
        request: SafeLocationCreateRequest

    ): Response<SafeLocationResponse>

    @PUT("safe-location/{location_id}")
    suspend fun updateSafeLocation(

        @Path("location_id")
        locationId: Int,

        @Body
        request: SafeLocationCreateRequest

    ): Response<SafeLocationResponse>



    @DELETE("safe-location/{location_id}")
    suspend fun deleteSafeLocation(

        @Path("location_id")
        locationId: Int

    ): Response<Unit>


    @GET("safe-location/detail/{location_id}")
    suspend fun getSafeLocationById(
        @Path("location_id") locationId: Int
    ): Response<SafeLocationResponse>

}