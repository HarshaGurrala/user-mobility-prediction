package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.SafeLocationResponse
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SafeZonesViewModel : ViewModel() {

    private val _safeLocations =
        MutableStateFlow<List<SafeLocationResponse>>(emptyList())

    val safeLocations: StateFlow<List<SafeLocationResponse>> =
        _safeLocations.asStateFlow()


    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading.asStateFlow()


    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error.asStateFlow()


    fun loadSafeLocations(userId: Int) {

        viewModelScope.launch {

            try {

                _loading.value = true
                _error.value = null

                val response =
                    RetrofitClient.safeLocationApi
                        .getSafeLocations(userId)

                if (response.isSuccessful) {

                    _safeLocations.value =
                        response.body() ?: emptyList()

                } else {

                    _safeLocations.value = emptyList()

                    _error.value =
                        "Failed to load safe zones: ${response.code()}"

                }

            } catch (e: Exception) {

                _safeLocations.value = emptyList()

                _error.value =
                    e.message ?: "Unable to load safe zones"

            } finally {

                _loading.value = false

            }
        }
    }
}