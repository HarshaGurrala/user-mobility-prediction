package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.usermobilityprediction.app.data.model.SearchUserResponse
import com.usermobilityprediction.app.data.repository.GuardianRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GuardianConnectViewModel : ViewModel() {

    private val repository =
        GuardianRepository()


    // =====================================================
    // LOADING
    // =====================================================

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading


    // =====================================================
    // SEARCHED USER
    // =====================================================

    private val _user =
        MutableStateFlow<SearchUserResponse?>(null)

    val user: StateFlow<SearchUserResponse?> =
        _user


    // =====================================================
    // MESSAGE
    // =====================================================

    private val _message =
        MutableStateFlow<String?>(null)

    val message: StateFlow<String?> =
        _message


    // =====================================================
    // ERROR
    // =====================================================

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error


    // =====================================================
    // REQUEST SUCCESS
    // =====================================================

    private val _requestSuccess =
        MutableStateFlow(false)

    val requestSuccess: StateFlow<Boolean> =
        _requestSuccess


    // =====================================================
    // SEARCH USER
    // =====================================================

    fun searchUser(
        safePathId: String
    ) {

        viewModelScope.launch {

            _loading.value = true

            _error.value = null

            _message.value = null

            _user.value = null

            try {

                val response =
                    repository.searchUser(
                        safePathId
                    )


                if (
                    response.isSuccessful
                ) {

                    _user.value =
                        response.body()

                } else {

                    _error.value =
                        response.errorBody()
                            ?.string()
                            ?: "User not found"
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


    // =====================================================
    // SEND GUARDIAN REQUEST
    // =====================================================

    fun sendRequest(
        safePathId: String
    ) {

        viewModelScope.launch {

            _loading.value = true

            _error.value = null

            _message.value = null

            // Reset before every request
            _requestSuccess.value = false


            try {

                val response =
                    repository.sendGuardianRequest(
                        safePathId
                    )


                if (
                    response.isSuccessful
                ) {

                    _message.value =
                        response.body()
                            ?.get("message")
                            ?: "Request Sent Successfully"


                    // ==========================================
                    // REQUEST REALLY SUCCEEDED
                    // ==========================================

                    _requestSuccess.value =
                        true


                } else {

                    _requestSuccess.value =
                        false

                    _error.value =
                        response.errorBody()
                            ?.string()
                            ?: "Unable to send request"
                }


            } catch (e: Exception) {

                _requestSuccess.value =
                    false

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            } finally {

                _loading.value = false
            }
        }
    }
}