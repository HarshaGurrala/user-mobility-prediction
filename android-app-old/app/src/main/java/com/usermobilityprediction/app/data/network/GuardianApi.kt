package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GuardianApi {

    @POST("guardian/connect")
    suspend fun connectGuardian(
        @Body request: GuardianConnectRequest
    ): Response<MessageResponse>

    @GET("guardian/pending")
    suspend fun getPendingRequests(): Response<List<GuardianPendingRequest>>

    @PUT("guardian/accept/{requestId}")
    suspend fun acceptRequest(
        @Path("requestId") requestId: Int
    ): Response<MessageResponse>

    @PUT("guardian/reject/{requestId}")
    suspend fun rejectRequest(
        @Path("requestId") requestId: Int
    ): Response<MessageResponse>

    @GET("guardian/connected-users")
    suspend fun getConnectedUsers(): Response<List<ConnectedUser>>
}