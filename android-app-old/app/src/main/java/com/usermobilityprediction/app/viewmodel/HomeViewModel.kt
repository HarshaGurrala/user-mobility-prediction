package com.usermobilityprediction.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.mock.MockRepository
import com.usermobilityprediction.app.data.models.AppNotification
import com.usermobilityprediction.app.data.models.LocationPoint
import com.usermobilityprediction.app.data.models.Prediction
import com.usermobilityprediction.app.data.models.SafeZone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = MockRepository()

    private val _locations = MutableStateFlow<List<LocationPoint>>(emptyList())
    val locations: StateFlow<List<LocationPoint>> = _locations

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _predictions = MutableStateFlow<List<Prediction>>(emptyList())
    val predictions: StateFlow<List<Prediction>> = _predictions

    private val _safeZones = MutableStateFlow<List<SafeZone>>(emptyList())
    val safeZones: StateFlow<List<SafeZone>> = _safeZones

    private val _notifications = MutableStateFlow<List<AppNotification>>(emptyList())
    val notifications: StateFlow<List<AppNotification>> = _notifications

    init {
        refreshAll()
    }

    fun refreshAll() {
        viewModelScope.launch {
            _isLoading.value = true
            _locations.value = repo.getRecentLocations()
            _predictions.value = repo.getPredictions()
            _safeZones.value = repo.getSafeZones()
            _notifications.value = repo.getNotifications()
            _isLoading.value = false
        }
    }
}
