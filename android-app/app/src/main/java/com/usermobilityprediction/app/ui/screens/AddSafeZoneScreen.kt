package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.google.android.gms.maps.model.LatLng
import com.usermobilityprediction.app.data.model.SafeLocationCreateRequest
import com.usermobilityprediction.app.ui.components.MapPicker
import com.usermobilityprediction.app.viewmodel.SafeLocationViewModel

import android.location.Geocoder
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
@Composable
fun AddSafeZoneScreen(

    navController: NavController,

    userId: Int,

    viewModel: SafeLocationViewModel = viewModel()

) {




    var searchQuery by remember {
        mutableStateOf("")
    }


    var safeZoneName by remember {
        mutableStateOf("")
    }


    var selectedLocation by remember {
        mutableStateOf<LatLng?>(null)
    }


    var radius by remember {
        mutableStateOf(100f)
    }

    val context = LocalContext.current



    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(20.dp)
            .verticalScroll(
                rememberScrollState()
            )

    ){


        IconButton(

            onClick = {
                navController.popBackStack()
            }

        ) {

            Icon(

                imageVector = Icons.Default.ArrowBack,

                contentDescription = null,

                tint = Color.White

            )

        }



        Text(

            text = "Add Safe Zone",

            color = Color.White,

            style = MaterialTheme.typography.headlineMedium

        )



        Spacer(
            modifier = Modifier.height(24.dp)
        )



        OutlinedTextField(

            value = searchQuery,

            onValueChange = {

                searchQuery = it

            },

            label = {

                Text("Search Location")

            },

            modifier = Modifier.fillMaxWidth()

        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        OutlinedTextField(

            value = safeZoneName,

            onValueChange = {

                safeZoneName = it

            },

            label = {

                Text("Safe Zone Name")

            },

            modifier = Modifier.fillMaxWidth()

        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        Button(

            modifier = Modifier.fillMaxWidth(),

            onClick = {

                try {

                    val geocoder =
                        Geocoder(
                            context,
                            Locale.getDefault()
                        )


                    val result =
                        geocoder.getFromLocationName(
                            searchQuery,
                            1
                        )


                    if (!result.isNullOrEmpty()) {


                        val address =
                            result[0]


                        selectedLocation =
                            LatLng(
                                address.latitude,
                                address.longitude
                            )


                    }


                } catch (e: Exception) {


                    e.printStackTrace()


                }


            }

        ) {

            Text(
                "Search Location"
            )

        }



        Spacer(
            modifier = Modifier.height(16.dp)
        )



        Text(

            text = "Select Safe Zone Location",

            color = Color.White

        )



        Spacer(
            modifier = Modifier.height(12.dp)
        )



        Box(

            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)

        ) {


            MapPicker(

                selectedLocation = selectedLocation,

                onLocationSelected = {

                    selectedLocation = it

                }

            )


        }



        selectedLocation?.let {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(

                text = "Location Selected ✓",

                color = Color(0xFF22C55E)

            )

        }



        Spacer(
            modifier = Modifier.height(16.dp)
        )



        Text(

            text = "Radius: ${radius.toInt()} meters",

            color = Color.White

        )



        Slider(

            value = radius,

            onValueChange = {

                radius = it

            },

            valueRange = 50f..1000f

        )



        Spacer(
            modifier = Modifier.height(20.dp)
        )



        Button(

            modifier = Modifier.fillMaxWidth(),

            onClick = {


                if (
                    safeZoneName.isBlank()
                    ||
                    selectedLocation == null
                ) {

                    return@Button

                }


                val location =
                    selectedLocation!!



                viewModel.addSafeLocation(

                    userId,


                    SafeLocationCreateRequest(

                        locationName = safeZoneName,

                        latitude = location.latitude,

                        longitude = location.longitude,

                        radius = radius.toDouble()

                    )

                ) {


                    navController.popBackStack()

                }


            }

        ) {


            Text(

                text = "Save Safe Zone"

            )


        }


    }


}