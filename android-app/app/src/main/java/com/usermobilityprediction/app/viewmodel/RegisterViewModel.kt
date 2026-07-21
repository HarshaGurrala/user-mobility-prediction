package com.usermobilityprediction.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(

    val isLoading: Boolean = false,

    val isSuccess: Boolean = false,

    val error: String? = null

)

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RegisterUiState()
    )

    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(

        fullName: String,

        email: String,

        phone: String?,

        password: String

    ) {

        viewModelScope.launch {

            _uiState.value = RegisterUiState(
                isLoading = true
            )

            try {

                val response = repository.register(
                    fullName,
                    email,
                    phone,
                    password,
                    "USER"
                )

                if (response.isSuccessful) {

                    _uiState.value = RegisterUiState(
                        isSuccess = true
                    )

                } else {

                    _uiState.value = RegisterUiState(
                        error = "Registration failed"
                    )

                }

            } catch (e: Exception) {

                _uiState.value = RegisterUiState(
                    error = e.localizedMessage
                )

            }

        }

    }

}