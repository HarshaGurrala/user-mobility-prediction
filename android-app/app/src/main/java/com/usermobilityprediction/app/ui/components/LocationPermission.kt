package com.usermobilityprediction.app.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit
) {

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fine =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

            val coarse =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (fine || coarse) {
                onPermissionGranted()
            }
        }

    LaunchedEffect(Unit) {

        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}