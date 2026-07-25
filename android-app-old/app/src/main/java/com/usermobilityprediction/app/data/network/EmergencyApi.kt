package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.models.EmergencyContactRequest
import com.usermobilityprediction.app.data.models.EmergencyContactResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface EmergencyApi {


    @POST("emergency/{user_id}")
    suspend fun addContact(
        @Path("user_id") userId: Int,
        @Body request: EmergencyContactRequest
    ): Response<EmergencyContactResponse>


    @GET("emergency/{user_id}")
    suspend fun getContacts(
        @Path("user_id") userId: Int
    ): Response<List<EmergencyContactResponse>>

}