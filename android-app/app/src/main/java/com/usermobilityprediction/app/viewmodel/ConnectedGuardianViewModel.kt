package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.ConnectedGuardianResponse
import com.usermobilityprediction.app.data.repository.GuardianRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConnectedGuardianViewModel : ViewModel() {

    private val repository = GuardianRepository()

    private val _guardians =
        MutableStateFlow<List<ConnectedGuardianResponse>>(emptyList())

    val guardians: StateFlow<List<ConnectedGuardianResponse>> =
        _guardians

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    fun loadGuardians() {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            try {

                val response =
                    repository.getMyGuardians()

                if (response.isSuccessful) {

                    _guardians.value =
                        response.body() ?: emptyList()

                } else {

                    _error.value =
                        response.errorBody()
                            ?.string()
                            ?: "Unable to load guardians"
                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            } finally {

                _loading.value = false
            }
        }
    }
}