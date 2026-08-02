package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.model.EmergencyContactResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import com.usermobilityprediction.app.data.model.EmergencyContactCreateRequest

import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.DELETE

interface EmergencyContactApi {

    @GET("emergency/{user_id}")
    suspend fun getContacts(
        @Path("user_id")
        userId: Int
    ): Response<List<EmergencyContactResponse>>

    @POST("emergency/{user_id}")
    suspend fun addContact(
        @Path("user_id")
        userId: Int,
        @Body
        request: EmergencyContactCreateRequest
    ): Response<EmergencyContactResponse>

    @PUT("emergency/{contact_id}")
    suspend fun updateContact(
        @Path("contact_id") contactId: Int,
        @Body request: EmergencyContactCreateRequest
    ): Response<EmergencyContactResponse>


    @DELETE("emergency/{contact_id}")
    suspend fun deleteContact(
        @Path("contact_id") contactId: Int
    ): Response<Unit>

}