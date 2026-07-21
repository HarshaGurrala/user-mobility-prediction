package com.usermobilityprediction.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.models.ProfileResponse
import com.usermobilityprediction.app.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ProfileState {

    object Loading : ProfileState()

    data class Success(
        val profile: ProfileResponse
    ) : ProfileState()

    data class Error(
        val message: String
    ) : ProfileState()
}

class ProfileViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        ProfileRepository(application)

    private val _state =
        MutableStateFlow<ProfileState>(
            ProfileState.Loading
        )

    val state: StateFlow<ProfileState> =
        _state

    init {
        loadProfile()
    }

    fun loadProfile() {

        viewModelScope.launch {

            try {

                val response =
                    repository.getProfile()

                if (
                    response.isSuccessful &&
                    response.body() != null
                ) {

                    _state.value =
                        ProfileState.Success(
                            response.body()!!
                        )

                } else {

                    _state.value =
                        ProfileState.Error(
                            "Failed to load profile"
                        )
                }

            } catch (e: Exception) {

                _state.value =
                    ProfileState.Error(
                        e.localizedMessage
                            ?: "Network Error"
                    )
            }
        }
    }
}