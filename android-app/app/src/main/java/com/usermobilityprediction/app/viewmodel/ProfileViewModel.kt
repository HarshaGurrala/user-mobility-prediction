package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.UserResponse
import com.usermobilityprediction.app.data.model.UserUpdateRequest
import com.usermobilityprediction.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = AuthRepository()


    private val _user =
        MutableStateFlow<UserResponse?>(null)

    val user: StateFlow<UserResponse?> =
        _user


    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading


    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error


    private val _updateLoading =
        MutableStateFlow(false)

    val updateLoading: StateFlow<Boolean> =
        _updateLoading


    private val _updateSuccess =
        MutableStateFlow(false)

    val updateSuccess: StateFlow<Boolean> =
        _updateSuccess


    private val _updateError =
        MutableStateFlow<String?>(null)

    val updateError: StateFlow<String?> =
        _updateError


    fun loadCurrentUser() {

        viewModelScope.launch {

            _loading.value = true

            _error.value = null

            try {

                val response =
                    repository.getCurrentUser()

                if (response.isSuccessful) {

                    val userResponse =
                        response.body()

                    if (userResponse != null) {

                        _user.value =
                            userResponse

                    } else {

                        _error.value =
                            "Invalid server response"
                    }

                } else {

                    _error.value =
                        response.errorBody()
                            ?.string()
                            ?: "Failed to load profile"
                }

            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Unable to connect to server"

            } finally {

                _loading.value = false
            }
        }
    }


    fun updateCurrentUser(
        fullName: String,
        email: String,
        phoneNumber: String
    ) {

        viewModelScope.launch {

            _updateLoading.value = true

            _updateSuccess.value = false

            _updateError.value = null

            try {

                val request =
                    UserUpdateRequest(
                        full_name = fullName.trim(),
                        email = email.trim(),
                        phone_number =
                            phoneNumber
                                .trim()
                                .ifBlank {
                                    null
                                }
                    )


                val response =
                    repository.updateCurrentUser(
                        request
                    )


                if (response.isSuccessful) {

                    val updatedUser =
                        response.body()

                    if (updatedUser != null) {

                        // Update local ViewModel state
                        _user.value =
                            updatedUser

                        _updateSuccess.value =
                            true

                    } else {

                        _updateError.value =
                            "Invalid server response"
                    }

                } else {

                    _updateError.value =
                        response.errorBody()
                            ?.string()
                            ?: "Failed to update profile"
                }

            } catch (e: Exception) {

                _updateError.value =
                    e.localizedMessage
                        ?: "Unable to connect to server"

            } finally {

                _updateLoading.value = false
            }
        }
    }


    fun resetUpdateState() {

        _updateSuccess.value = false

        _updateError.value = null
    }
}