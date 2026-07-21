package com.usermobilityprediction.app.data.location

import android.content.Context
import android.location.Geocoder
import android.os.Build
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume


class GeocodingService(
    private val context: Context
) {


    private val geocoder =
        Geocoder(
            context,
            Locale.getDefault()
        )



    suspend fun getLocationName(
        latitude: Double,
        longitude: Double
    ): String {


        return try {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {


                suspendCancellableCoroutine { continuation ->


                    geocoder.getFromLocation(
                        latitude,
                        longitude,
                        5
                    ) { addresses ->


                        val address =
                            addresses.firstOrNull()



                        val name =
                            address?.let {


                                listOfNotNull(
                                    it.featureName,
                                    it.locality,
                                    it.subAdminArea
                                )
                                    .distinct()
                                    .joinToString(", ")

                            }



                        continuation.resume(
                            if (!name.isNullOrBlank())
                                name
                            else
                                "Unknown Location"
                        )
                    }
                }


            } else {


                val addresses =
                    geocoder.getFromLocation(
                        latitude,
                        longitude,
                        5
                    )


                val address =
                    addresses?.firstOrNull()



                val name =
                    address?.let {


                        listOfNotNull(
                            it.featureName,
                            it.locality,
                            it.subAdminArea
                        )
                            .distinct()
                            .joinToString(", ")

                    }



                if (!name.isNullOrBlank())
                    name
                else
                    "Unknown Location"

            }


        } catch (e: Exception) {


            "Unknown Location"

        }
    }
}