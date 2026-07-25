package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.ui.components.dashboard.AIMobilityOverviewCard
import com.usermobilityprediction.app.viewmodel.HomeViewModel
import com.usermobilityprediction.app.ui.components.dashboard.ProfileStatusCard
import com.usermobilityprediction.app.ui.components.dashboard.SafeZonePremiumPanel
import com.usermobilityprediction.app.ui.components.dashboard.AIPredictionDetailsCard
import com.usermobilityprediction.app.ui.components.dashboard.CurrentLocationIntelligenceCard
import com.usermobilityprediction.app.ui.components.dashboard.GuardianSafetyCenter
import com.usermobilityprediction.app.ui.components.dashboard.EmergencyReadinessPanel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usermobilityprediction.app.viewmodel.LocationViewModel
import com.usermobilityprediction.app.viewmodel.LocationViewModelFactory





@Composable
fun HomeDashboardScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavController? = null

) {
    val context = LocalContext.current

    val locationViewModel: LocationViewModel = viewModel(
        factory = LocationViewModelFactory(context)
    )

    val currentLocation by locationViewModel.currentLocation.collectAsState()


    LaunchedEffect(Unit) {
        locationViewModel.startLocationTracking()
    }

    val locations by homeViewModel.locations.collectAsState()
    val predictions by homeViewModel.predictions.collectAsState()


    LaunchedEffect(Unit) {
        locationViewModel.startLocationTracking()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {


            item {

                Spacer(
                    modifier = Modifier.height(30.dp)
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Column {

                        Text(
                            text = "Welcome back",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )


                        Text(
                            text = "Mobility Guardian",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = Color(0xFF4DB8FF),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }


            item {

                AIMobilityOverviewCard()
            }

            item {

                ProfileStatusCard()
            }


            item {

                CurrentLocationIntelligenceCard(
                    locationName = currentLocation?.locationName,
                    latitude = currentLocation?.latitude,
                    longitude = currentLocation?.longitude,
                    timestamp = currentLocation?.timestamp
                )
            }


            item {

                DashboardGlassCard(
                    icon = Icons.Outlined.Security,
                    title = "Guardian Protection",
                    value = "Active"
                )
            }


            item {

                AIPredictionDetailsCard(
                    destination =
                        if (predictions.isNotEmpty())
                            predictions.first().placeName
                        else
                            "Analyzing movement pattern",

                    confidence =
                        if (predictions.isNotEmpty())
                            predictions.first().confidence.toInt()
                        else
                            0,

                    eta =
                        if (predictions.isNotEmpty())
                            predictions.first().eta
                        else
                            "Calculating..."
                )
            }



            item {

                SafeZonePremiumPanel()
                GuardianSafetyCenter()
            }


            item {
                EmergencyReadinessPanel()


                Button(
                    onClick = {

                        navController?.navigate("emergency")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935)
                    )
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Emergency Alert",
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            item {

                Spacer(
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}



@Composable
private fun DashboardGlassCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0x22FFFFFF),
                        Color(0x1100A8FF)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4DB8FF),
            modifier = Modifier.size(32.dp)
        )


        Spacer(
            modifier = Modifier.width(16.dp)
        )


        Column {

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 13.sp
            )


            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}