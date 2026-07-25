package com.usermobilityprediction.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.models.ConnectedUser
import com.usermobilityprediction.app.data.models.GuardianPendingRequest
import com.usermobilityprediction.app.data.repository.GuardianRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GuardianViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        GuardianRepository(application)

    private val _pendingRequests =
        MutableStateFlow<List<GuardianPendingRequest>>(emptyList())
    val pendingRequests: StateFlow<List<GuardianPendingRequest>> =
        _pendingRequests

    private val _connectedUsers =
        MutableStateFlow<List<ConnectedUser>>(emptyList())
    val connectedUsers: StateFlow<List<ConnectedUser>> =
        _connectedUsers

    private val _loading =
        MutableStateFlow(false)
    val loading: StateFlow<Boolean> =
        _loading

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    private val _success =
        MutableStateFlow<String?>(null)

    val success: StateFlow<String?> =
        _success

    fun loadPendingRequests() {

        viewModelScope.launch {

            _loading.value = true

            try {

                val response =
                    repository.getPendingRequests()

                if (response.isSuccessful && response.body() != null) {

                    _pendingRequests.value =
                        response.body()!!

                }

            } finally {

                _loading.value = false

            }
        }
    }

    fun loadConnectedUsers() {

        viewModelScope.launch {

            _loading.value = true

            try {

                val response =
                    repository.getConnectedUsers()

                if (response.isSuccessful && response.body() != null) {

                    _connectedUsers.value =
                        response.body()!!

                }

            } finally {

                _loading.value = false

            }
        }
    }

    fun acceptRequest(id: Int) {

        viewModelScope.launch {

            _loading.value = true

            try {

                repository.acceptRequest(id)

                loadPendingRequests()

            } finally {

                _loading.value = false

            }
        }
    }

    fun rejectRequest(id: Int) {

        viewModelScope.launch {

            _loading.value = true

            try {

                repository.rejectRequest(id)

                loadPendingRequests()

            } finally {

                _loading.value = false

            }
        }
    }

    fun connectGuardian(
        safePathId: String,
        onSuccess: () -> Unit = {}
    ) {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null
            _success.value = null

            try {

                val response =
                    repository.connectGuardian(safePathId)

                if (response.isSuccessful) {

                    _success.value =
                        response.body()?.message
                            ?: "Guardian request sent."

                    onSuccess()

                } else {

                    _error.value =
                        response.errorBody()?.string()
                            ?: "Unable to send guardian request."

                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage ?: "Network Error"

            } finally {

                _loading.value = false

            }
        }
    }
}