package com.usermobilityprediction.app.data.repository

import com.usermobilityprediction.app.data.model.GuardianRequest
import com.usermobilityprediction.app.data.model.SearchUserResponse
import com.usermobilityprediction.app.data.model.PendingRequestResponse
import com.usermobilityprediction.app.data.network.RetrofitClient
import retrofit2.Response


class GuardianRepository {


    // ===============================
    // Guardian searches user
    // ===============================

    suspend fun searchUser(
        safePathId: String
    ): Response<SearchUserResponse> {


        return RetrofitClient.api.searchUser(
            safePathId
        )

    }



    // ===============================
    // Guardian sends request
    // ===============================

    suspend fun sendGuardianRequest(
        safePathId: String
    ): Response<Map<String, String>> {


        return RetrofitClient.api.connectGuardian(

            GuardianRequest(
                safe_path_id = safePathId
            )

        )

    }



    // ===============================
    // User gets pending requests
    // ===============================

    suspend fun getPendingRequests():

            Response<List<PendingRequestResponse>> {


        return RetrofitClient.api.getPendingRequests()

    }



    // ===============================
    // User accepts request
    // ===============================

    suspend fun acceptRequest(
        requestId: Int
    ): Response<Map<String, String>> {


        return RetrofitClient.api.acceptGuardianRequest(

            requestId

        )

    }



    // ===============================
    // User rejects request
    // ===============================

    suspend fun rejectRequest(
        requestId: Int
    ): Response<Map<String, String>> {


        return RetrofitClient.api.rejectGuardianRequest(

            requestId

        )

    }

}