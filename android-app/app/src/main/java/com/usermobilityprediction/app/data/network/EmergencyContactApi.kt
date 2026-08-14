
package com.usermobilityprediction.app.data.network

import com.usermobilityprediction.app.data.model.EmergencyContactResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface EmergencyContactApi {

    // ==========================================================
    // GET GUARDIAN-ASSIGNED EMERGENCY CONTACTS
    // ==========================================================

    @GET("emergency/{user_id}")
    suspend fun getContacts(
        @Path("user_id")
        userId: Int
    ): Response<List<EmergencyContactResponse>>

}

