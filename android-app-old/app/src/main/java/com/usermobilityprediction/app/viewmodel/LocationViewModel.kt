package com.usermobilityprediction.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.location.LocationData
import com.usermobilityprediction.app.data.location.LocationService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class LocationViewModel(
    private val locationService: LocationService
) : ViewModel() {


    private val _currentLocation =
        MutableStateFlow<LocationData?>(null)

    val currentLocation: StateFlow<LocationData?> =
        _currentLocation.asStateFlow()



    fun startLocationTracking() {

        viewModelScope.launch {

            locationService
                .getLocationUpdates()
                .collect { location ->

                    _currentLocation.value =
                        location
                }
        }
    }
}