package com.usermobilityprediction.app.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationRequest as GoogleLocationRequest

import com.usermobilityprediction.app.data.model.LocationRequest as UploadLocationRequest
import com.usermobilityprediction.app.data.network.RetrofitClient
import com.usermobilityprediction.app.data.storage.TokenManager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


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

                interval = 5000

                fastestInterval = 5000

                priority =
                    Priority.PRIORITY_HIGH_ACCURACY
            }



    @SuppressLint("MissingPermission")
    fun startTracking() {


        fusedLocationClient.requestLocationUpdates(

            locationRequest,


            object : LocationCallback() {


                override fun onLocationResult(
                    result: LocationResult
                ) {


                    val location =
                        result.lastLocation
                            ?: return



                    android.util.Log.d(
                        "LOCATION_TEST",
                        "Latitude=${location.latitude}, Longitude=${location.longitude}, Accuracy=${location.accuracy}"
                    )



                    _currentLocation.value =
                        location



                    val userId =
                        tokenManager.getUserId()



                    if (userId == -1) return



                    CoroutineScope(
                        Dispatchers.IO
                    ).launch {


                        try {


                            RetrofitClient.api.uploadLocation(

                                userId,


                                UploadLocationRequest(

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
                                e.message ?: "Upload failed"
                            )
                        }
                    }
                }
            },


            Looper.getMainLooper()
        )
    }



    fun stopTracking() {

        fusedLocationClient.flushLocations()
    }
}