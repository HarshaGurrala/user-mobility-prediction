package com.usermobilityprediction.app.data.repository

import android.content.Context
import com.usermobilityprediction.app.data.models.*
import com.usermobilityprediction.app.data.network.RetrofitClient
import retrofit2.Response

class GuardianRepository(
    context: Context
) {

    private val api = RetrofitClient.guardianApi(context)

    suspend fun connectGuardian(
        safePathId: String
    ): Response<MessageResponse> {

        return api.connectGuardian(
            GuardianConnectRequest(
                safePathId = safePathId
            )
        )
    }

    suspend fun getPendingRequests(): Response<List<GuardianPendingRequest>> {
        return api.getPendingRequests()
    }

    suspend fun acceptRequest(
        requestId: Int
    ): Response<MessageResponse> {
        return api.acceptRequest(requestId)
    }

    suspend fun rejectRequest(
        requestId: Int
    ): Response<MessageResponse> {
        return api.rejectRequest(requestId)
    }

    suspend fun getConnectedUsers(): Response<List<ConnectedUser>> {
        return api.getConnectedUsers()
    }
}