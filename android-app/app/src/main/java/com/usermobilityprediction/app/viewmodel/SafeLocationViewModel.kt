package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.SafeLocationCreateRequest
import com.usermobilityprediction.app.data.model.SafeLocationResponse
import com.usermobilityprediction.app.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SafeLocationViewModel : ViewModel() {


    private val _safeLocations =
        MutableStateFlow<List<SafeLocationResponse>>(emptyList())

    val safeLocations: StateFlow<List<SafeLocationResponse>> =
        _safeLocations.asStateFlow()

    private val _selectedLocation =
        MutableStateFlow<SafeLocationResponse?>(null)

    val selectedLocation =
        _selectedLocation.asStateFlow()


    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading.asStateFlow()



    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error.asStateFlow()



    fun loadSafeLocations(
        userId: Int
    ) {

        viewModelScope.launch {

            try {

                _loading.value = true
                _error.value = null


                val response =
                    RetrofitClient
                        .safeLocationApi
                        .getSafeLocations(userId)


                if (response.isSuccessful) {

                    _safeLocations.value =
                        response.body()
                            ?: emptyList()

                } else {

                    _error.value =
                        "Failed to load safe zones"

                }


            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            }
            finally {

                _loading.value = false

            }

        }

    }



    fun addSafeLocation(
        userId: Int,
        request: SafeLocationCreateRequest,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient
                        .safeLocationApi
                        .addSafeLocation(
                            userId,
                            request
                        )


                if (response.isSuccessful) {

                    loadSafeLocations(userId)

                    onSuccess()

                } else {

                    _error.value =
                        "Unable to add safe zone"

                }


            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            }

        }

    }

    fun deleteSafeLocation(
        locationId: Int,
        userId: Int,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient
                        .safeLocationApi
                        .deleteSafeLocation(
                            locationId
                        )


                if (response.isSuccessful) {

                    loadSafeLocations(userId)

                    onSuccess()

                } else {

                    _error.value =
                        "Unable to delete safe zone"

                }


            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            }

        }

    }

    fun loadSafeLocationById(
        locationId: Int
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient
                        .safeLocationApi
                        .getSafeLocationById(
                            locationId
                        )


                if (response.isSuccessful) {

                    _selectedLocation.value =
                        response.body()

                } else {

                    _error.value =
                        "Failed to load safe zone"

                }


            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            }

        }

    }



    fun updateSafeLocation(

        locationId: Int,
        userId: Int,
        request: SafeLocationCreateRequest,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    RetrofitClient
                        .safeLocationApi
                        .updateSafeLocation(
                            locationId,
                            request
                        )
                android.util.Log.d(
                    "SAFE_UPDATE",
                    "updateSafeLocation() called: id=$locationId"
                )


                if (response.isSuccessful) {

                    loadSafeLocations(userId)

                    onSuccess()

                } else {

                    val errorBody =
                        response.errorBody()?.string()

                    android.util.Log.e(
                        "SAFE_DELETE",
                        "DELETE FAILED code=${response.code()} body=$errorBody"
                    )

                    _error.value =
                        "Unable to delete safe zone"

                }


            } catch (e: Exception) {

                _error.value =
                    e.localizedMessage
                        ?: "Network Error"

            }

        }

    }

}