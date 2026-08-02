package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.EmergencyContactResponse
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.usermobilityprediction.app.data.model.EmergencyContactCreateRequest
class EmergencyContactViewModel : ViewModel() {

    private val _contacts =
        MutableStateFlow<List<EmergencyContactResponse>>(emptyList())

    val contacts: StateFlow<List<EmergencyContactResponse>> =
        _contacts.asStateFlow()


    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading.asStateFlow()


    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error.asStateFlow()




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

                    _error.value =
                        "Failed to load emergency contacts"

                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Unable to load emergency contacts"

            } finally {

                _loading.value = false

            }

        }

    }

    fun addContact(
        userId: Int,
        request: EmergencyContactCreateRequest,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient
                        .emergencyContactApi
                        .addContact(
                            userId,
                            request
                        )

                if (response.isSuccessful) {

                    loadContacts(userId)

                    onSuccess()

                } else {

                    _error.value = "Unable to add contact"

                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage ?: "Network Error"

            }

        }

    }

    fun updateContact(
        contactId: Int,
        userId: Int,
        request: EmergencyContactCreateRequest,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient
                        .emergencyContactApi
                        .updateContact(
                            contactId,
                            request
                        )

                if (response.isSuccessful) {

                    loadContacts(userId)

                    onSuccess()

                } else {

                    _error.value =
                        "Unable to update contact"

                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            }

        }

    }



    fun deleteContact(
        contactId: Int,
        userId: Int,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient
                        .emergencyContactApi
                        .deleteContact(
                            contactId
                        )


                if (response.isSuccessful) {

                    loadContacts(userId)

                    onSuccess()

                } else {

                    _error.value =
                        "Unable to delete contact"

                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            }

        }

    }

}