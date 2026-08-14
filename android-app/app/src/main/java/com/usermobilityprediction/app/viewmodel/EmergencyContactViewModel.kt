
package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.usermobilityprediction.app.data.model.EmergencyContactResponse
import com.usermobilityprediction.app.data.network.RetrofitClient

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class EmergencyContactViewModel : ViewModel() {

    // ==========================================================
    // CONTACTS
    // ==========================================================

    private val _contacts =
        MutableStateFlow<List<EmergencyContactResponse>>(
            emptyList()
        )

    val contacts: StateFlow<List<EmergencyContactResponse>> =
        _contacts.asStateFlow()


    // ==========================================================
    // LOADING
    // ==========================================================

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading.asStateFlow()


    // ==========================================================
    // ERROR
    // ==========================================================

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error.asStateFlow()


    // ==========================================================
    // LOAD EMERGENCY CONTACTS
    // ==========================================================

    fun loadContacts(
        userId: Int
    ) {

        viewModelScope.launch {

            try {

                _loading.value = true
                _error.value = null

                val response =
                    RetrofitClient
                        .emergencyContactApi
                        .getContacts(userId)


                if (response.isSuccessful) {

                    _contacts.value =
                        response.body()
                            ?: emptyList()

                } else {

                    _contacts.value =
                        emptyList()

                    _error.value =
                        "Failed to load emergency contacts"

                }

            } catch (e: Exception) {

                _contacts.value =
                    emptyList()

                _error.value =
                    e.localizedMessage
                        ?: "Unable to load emergency contacts"

            } finally {

                _loading.value = false

            }

        }

    }

}

