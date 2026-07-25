package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.RegisterRequest
import com.usermobilityprediction.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun register(
        fullName: String,
        email: String,
        phoneNumber: String?,
        password: String,
        role: String
    ) {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            try {

                val response = repository.register(
                    RegisterRequest(
                        full_name = fullName,
                        email = email,
                        phone_number = phoneNumber,
                        password = password,
                        role = role.uppercase()
                    )
                )

                if (response.isSuccessful) {

                    _success.value = true

                } else {

                    _error.value = response.errorBody()?.string()
                        ?: "Registration failed"
                }

            } catch (e: Exception) {

                _error.value = e.localizedMessage ?: "Unknown error"

            } finally {

                _loading.value = false
            }
        }
    }
}