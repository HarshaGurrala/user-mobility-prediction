package com.usermobilityprediction.app.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch


class LocationService(
    private val context: Context
) {


    private val fusedClient =
        LocationServices.getFusedLocationProviderClient(context)


    private val geocodingService =
        GeocodingService(context)



    @SuppressLint("MissingPermission")
    fun getLocationUpdates(): Flow<LocationData> = callbackFlow {


        val request =
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000L
            )
                .setMinUpdateIntervalMillis(3000L)
                .setWaitForAccurateLocation(true)
                .build()



        val callback =
            object : LocationCallback() {


                override fun onLocationResult(
                    result: LocationResult
                ) {


                    result.locations.lastOrNull()
                        ?.let { location ->


                            launch {


                                val locationName =
                                    geocodingService.getLocationName(
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    )


                                trySend(

                                    LocationData(

                                        latitude = location.latitude,

                                        longitude = location.longitude,

                                        locationName = locationName,

                                        timestamp =
                                            System.currentTimeMillis()
                                    )
                                )
                            }
                        }
                }
            }



        fusedClient.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        )



        awaitClose {

            fusedClient.removeLocationUpdates(
                callback
            )
        }
    }
}



data class LocationData(

    val latitude: Double,

    val longitude: Double,

    val locationName: String,

    val timestamp: Long
)