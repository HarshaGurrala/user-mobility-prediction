package com.usermobilityprediction.app.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.usermobilityprediction.app.viewmodel.LocationViewModel


@Composable
fun RequestLocationPermission(
    locationViewModel: LocationViewModel
) {

    val context = LocalContext.current


    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->


            val fineLocationGranted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true


            val coarseLocationGranted =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true


            if (fineLocationGranted || coarseLocationGranted) {

                locationViewModel.startLocationTracking()

            }
        }



    val hasPermission =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED



    LaunchedEffect(Unit) {

        if (hasPermission) {

            locationViewModel.startLocationTracking()

        } else {


            val permissions =

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    )

                } else {

                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                }


            permissionLauncher.launch(permissions)
        }
    }



    Button(
        onClick = {

            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    ) {

        Text(
            text = "Enable Location"
        )
    }
}