package com.usermobilityprediction.app.data.location

    import android.annotation.SuppressLint
            import android.content.Context
            import android.location.Location
            import android.os.Looper

            import com.google.android.gms.location.FusedLocationProviderClient
            import com.google.android.gms.location.LocationCallback
            import com.google.android.gms.location.LocationRequest as GoogleLocationRequest
            import com.google.android.gms.location.LocationResult
            import com.google.android.gms.location.LocationServices
            import com.google.android.gms.location.Priority

            import com.usermobilityprediction.app.data.model.LocationRequest as UploadLocationRequest
            import com.usermobilityprediction.app.data.network.RetrofitClient
            import com.usermobilityprediction.app.data.storage.TokenManager

            import kotlinx.coroutines.CoroutineScope
            import kotlinx.coroutines.Dispatchers
            import kotlinx.coroutines.flow.MutableStateFlow
            import kotlinx.coroutines.flow.StateFlow
            import kotlinx.coroutines.launch
            import android.location.Geocoder
    import com.usermobilityprediction.app.data.model.LocationRequest
    class LocationTracker(
        context: Context
    ) {

        companion object {

            private val _currentLocation =
                MutableStateFlow<Location?>(null)

            val currentLocation: StateFlow<Location?> =
                _currentLocation
        }

        private val fusedLocationClient:
                FusedLocationProviderClient =
            LocationServices
                .getFusedLocationProviderClient(context)

        private val tokenManager =
            TokenManager(context)

        private val locationRequest =
            GoogleLocationRequest
                .create()
                .apply {

                    interval = 60000
                    fastestInterval = 60000

                    priority =
                        Priority.PRIORITY_HIGH_ACCURACY
                }

        private val locationCallback =
            object : LocationCallback() {

                override fun onLocationResult(
                    result: LocationResult
                ) {

                    val location =
                        result.lastLocation ?: return

                    _currentLocation.value = location

                    val userId =
                        tokenManager.getUserId()

                    if (userId == -1) return


                    val geocoder = Geocoder(context)

                    val address = try {

                        geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        )?.firstOrNull()?.getAddressLine(0)

                    } catch (e: Exception) {

                        null
                    }

                    CoroutineScope(
                        Dispatchers.IO
                    ).launch {

                        try {

                            RetrofitClient.api.uploadLocation(
                                userId,
                                LocationRequest(
                                    latitude =
                                        location.latitude,

                                    longitude =
                                        location.longitude,

                                    accuracy =
                                        location.accuracy
                                )
                            )

                        } catch (e: Exception) {

                            android.util.Log.e(
                                "LOCATION_UPLOAD",
                                e.message
                                    ?: "Upload failed"
                            )
                        }
                    }
                }
            }

        @SuppressLint("MissingPermission")
        fun startTracking() {

            fusedLocationClient
                .requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
        }

        fun stopTracking() {

            fusedLocationClient
                .removeLocationUpdates(
                    locationCallback
                )
        }
    }
