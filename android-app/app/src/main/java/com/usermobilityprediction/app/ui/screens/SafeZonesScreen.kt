package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.data.storage.TokenManager
import com.usermobilityprediction.app.viewmodel.SafeZonesViewModel

@Composable
fun SafeZonesScreen(
    navController: NavController,
    userId: Int,
    viewModel: SafeZonesViewModel = viewModel()
){







    val safeLocations by
    viewModel.safeLocations.collectAsState()

    val loading by
    viewModel.loading.collectAsState()

    val error by
    viewModel.error.collectAsState()


    LaunchedEffect(userId) {

        if (userId != null) {

            viewModel.loadSafeLocations(userId)

        }

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF050505)
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.ArrowBack,

                        contentDescription =
                            "Back",

                        tint =
                            Color.White
                    )
                }

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Column {

                    Text(
                        text = "Safe Zones",
                        color = Color.White,
                        style =
                            MaterialTheme.typography
                                .headlineMedium
                    )

                    Text(
                        text =
                            "Your protected locations",

                        color = Color.Gray,

                        style =
                            MaterialTheme.typography
                                .bodySmall
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            if (loading) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color =
                            Color(0xFF3B82F6)
                    )
                }

            } else if (error != null) {

                Text(
                    text = error ?: "",
                    color = Color(0xFFEF4444),
                    modifier = Modifier.padding(8.dp)
                )

            } else if (safeLocations.isEmpty()) {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White.copy(
                                    alpha = 0.05f
                                )
                        )
                ) {

                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(24.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.LocationOn,

                            contentDescription =
                                null,

                            tint =
                                Color.Gray
                        )

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )

                        Text(
                            text =
                                "No Safe Zones",

                            color =
                                Color.White,

                            style =
                                MaterialTheme.typography
                                    .titleMedium
                        )

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                "No safe locations have been added yet.",

                            color =
                                Color.Gray
                        )
                    }
                }

            } else {

                safeLocations.forEach { location ->

                    SafeZoneCard(
                        location = location
                    )

                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SafeZoneCard(
    location: com.usermobilityprediction.app.data.model.SafeLocationResponse
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White.copy(
                        alpha = 0.05f
                    )
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Default.LocationOn,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFF3B82F6)
                )

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Text(
                    text =
                        location.locationName,

                    color =
                        Color.White,

                    style =
                        MaterialTheme.typography
                            .titleMedium
                )
            }


            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )


            Text(
                text =
                    "Latitude: ${location.latitude}",

                color =
                    Color.Gray,

                style =
                    MaterialTheme.typography
                        .bodySmall
            )


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            Text(
                text =
                    "Longitude: ${location.longitude}",

                color =
                    Color.Gray,

                style =
                    MaterialTheme.typography
                        .bodySmall
            )


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            Text(
                text =
                    "Safe radius: ${location.radius} m",

                color =
                    Color.Gray,

                style =
                    MaterialTheme.typography
                        .bodySmall
            )
        }
    }
}