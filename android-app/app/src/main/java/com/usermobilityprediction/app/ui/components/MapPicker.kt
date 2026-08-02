package com.usermobilityprediction.app.ui.components

import androidx.compose.runtime.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun MapPicker(

    selectedLocation: LatLng?,

    onLocationSelected: (LatLng) -> Unit

) {


    val cameraPositionState =
        rememberCameraPositionState()



    LaunchedEffect(selectedLocation) {


        selectedLocation?.let {


            cameraPositionState.animate(

                CameraUpdateFactory.newLatLngZoom(

                    it,

                    16f

                )

            )


        }


    }



    GoogleMap(

        cameraPositionState = cameraPositionState,


        onMapClick = {

            onLocationSelected(it)

        }


    ) {


        selectedLocation?.let {


            Marker(

                state = MarkerState(it),

                title = "Safe Zone"

            )


        }


    }


}