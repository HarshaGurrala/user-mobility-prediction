package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.navigation.Routes
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import com.usermobilityprediction.app.viewmodel.GuardianRequestViewModel
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.usermobilityprediction.app.data.storage.TokenManager


@Composable
fun SettingsScreen(
    navController: NavController
) {

    var locationTracking by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val tokenManager = remember {
        TokenManager(context.applicationContext)
    }

    val userId = tokenManager.getUserId()

    val guardianViewModel: GuardianRequestViewModel = viewModel()

    val requests by
    guardianViewModel.requests.collectAsState()

    val connectedGuardians by
    guardianViewModel.connectedGuardians.collectAsState()

    LaunchedEffect(Unit) {

        guardianViewModel.loadRequests()

        guardianViewModel.loadConnectedGuardians()

    }

    var backgroundLocation by remember {
        mutableStateOf(true)
    }

    var safetyAlerts by remember {
        mutableStateOf(true)
    }

    var emergencyAlerts by remember {
        mutableStateOf(true)
    }

    var aiPredictionAlerts by remember {
        mutableStateOf(true)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Text(
                text = "Manage your SafePath AI experience",
                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )


            // ==========================================================
            // ACCOUNT
            // ==========================================================

            SettingsSectionTitle(
                title = "Account"
            )


            SettingsItem(
                icon = Icons.Default.Person,
                title = "Edit Profile",
                subtitle = "Update your personal information",
                onClick = {

                    navController.navigate(
                        Routes.EDIT_PROFILE
                    )

                }
            )


            // ==========================================================
            // PENDING GUARDIAN REQUESTS
            // ==========================================================

            if (requests.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Pending Guardian Requests",
                    color = Color.White,
                    style =
                        MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
                )


                requests.forEach { request ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),

                        shape =
                            RoundedCornerShape(16.dp),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White.copy(
                                        alpha = 0.05f
                                    )
                            )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {


                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {


                                Icon(
                                    imageVector =
                                        Icons.Default.Person,

                                    contentDescription =
                                        null,

                                    tint =
                                        Color(0xFFF59E0B),

                                    modifier =
                                        Modifier.size(32.dp)
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(16.dp)
                                )


                                Column(
                                    modifier =
                                        Modifier.weight(1f)
                                ) {


                                    Text(
                                        text =
                                            request.guardian_name,

                                        color =
                                            Color.White,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleMedium
                                    )


                                    Text(
                                        text =
                                            request.guardian_email,

                                        color =
                                            Color.Gray,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall
                                    )


                                    request.guardian_phone?.let {

                                        Text(
                                            text = it,

                                            color =
                                                Color.Gray,

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodySmall
                                        )
                                    }


                                    Text(
                                        text =
                                            "SafePath ID: ${request.guardian_safe_path_id}",

                                        color =
                                            Color.Gray,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall
                                    )
                                }
                            }


                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )


                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.spacedBy(10.dp)
                            ) {


                                // ACCEPT
                                Button(
                                    onClick = {

                                        guardianViewModel
                                            .acceptRequest(
                                                request.request_id
                                            )

                                    },

                                    modifier =
                                        Modifier.weight(1f),

                                    colors =
                                        ButtonDefaults
                                            .buttonColors(
                                                containerColor =
                                                    Color(0xFF22C55E)
                                            ),

                                    shape =
                                        RoundedCornerShape(10.dp)
                                ) {

                                    Text(
                                        text = "Accept",
                                        color = Color.White
                                    )
                                }


                                // REJECT
                                OutlinedButton(
                                    onClick = {

                                        guardianViewModel
                                            .rejectRequest(
                                                request.request_id
                                            )

                                    },

                                    modifier =
                                        Modifier.weight(1f),

                                    shape =
                                        RoundedCornerShape(10.dp),

                                    colors =
                                        ButtonDefaults
                                            .outlinedButtonColors(
                                                contentColor =
                                                    Color(0xFFEF4444)
                                            )
                                ) {

                                    Text(
                                        text = "Reject"
                                    )
                                }
                            }
                        }
                    }
                }
            }


            // ==========================================================
            // CHANGE PASSWORD
            // ==========================================================

            SettingsItem(
                icon = Icons.Default.Lock,
                title = "Change Password",
                subtitle = "Update your account password",
                onClick = {

                    navController.navigate(
                        Routes.CHANGE_PASSWORD
                    )

                }
            )


//            SettingsItem(
//                icon = Icons.Default.AccountCircle,
//                title = "Profile Photo",
//                subtitle = "Change your profile picture",
//                onClick = {
//                    // Coming later
//                }
//            )
//
//
            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // ==========================================================
            // SAFETY
            // ==========================================================

            SettingsSectionTitle(
                title = "Safety"
            )


            SettingsSwitchItem(
                icon = Icons.Default.LocationOn,
                title = "Location Tracking",
                subtitle =
                    "Allow SafePath AI to track your location",
                checked = locationTracking,
                onCheckedChange = {
                    locationTracking = it
                }
            )


            SettingsSwitchItem(
                icon = Icons.Default.MyLocation,
                title = "Background Location",
                subtitle =
                    "Continue tracking when app is in background",
                checked = backgroundLocation,
                onCheckedChange = {
                    backgroundLocation = it
                }
            )


            SettingsItem(
                icon = Icons.Default.Shield,
                title = "Safe Zones",
                subtitle = "Manage your safe locations",
                onClick = {

                    navController.navigate(
                        Routes.SAFE_ZONES
                    )

                }
            )




            SettingsItem(
                icon = Icons.Default.Group,
                title = "Guardians",
                subtitle = "Manage connected guardians",
                onClick = {

                    navController.navigate(
                        Routes.GUARDIANS
                    )

                }
            )


            SettingsItem(
                icon = Icons.Default.Warning,
                title = "Emergency Contacts",
                subtitle = "View your emergency contacts",
                onClick = {

                    val userId =
                        tokenManager.getUserId()

                    navController.navigate(
                        "emergency_contacts/$userId"
                    )
                }
            )


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // ==========================================================
            // NOTIFICATIONS
            // ==========================================================

            SettingsSectionTitle(
                title = "Notifications"
            )


            SettingsSwitchItem(
                icon = Icons.Default.Notifications,
                title = "Safety Alerts",
                subtitle =
                    "Receive safety notifications",
                checked = safetyAlerts,
                onCheckedChange = {
                    safetyAlerts = it
                }
            )


            SettingsSwitchItem(
                icon = Icons.Default.Warning,
                title = "Emergency Alerts",
                subtitle =
                    "Receive emergency notifications",
                checked = emergencyAlerts,
                onCheckedChange = {
                    emergencyAlerts = it
                }
            )


            SettingsSwitchItem(
                icon = Icons.Default.Psychology,
                title = "AI Prediction Alerts",
                subtitle =
                    "Receive AI prediction notifications",
                checked = aiPredictionAlerts,
                onCheckedChange = {
                    aiPredictionAlerts = it
                }
            )


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // ==========================================================
            // PRIVACY
            // ==========================================================

            SettingsSectionTitle(
                title = "Privacy & Security"
            )


            SettingsItem(
                icon = Icons.Default.Security,
                title = "Privacy Settings",
                subtitle =
                    "Manage your privacy preferences",
                onClick = {
                    // Coming later
                }
            )


            SettingsItem(
                icon = Icons.Default.Devices,
                title = "Active Sessions",
                subtitle =
                    "Manage logged-in devices",
                onClick = {
                    // Coming later
                }
            )


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // ==========================================================
            // OTHER
            // ==========================================================

            SettingsSectionTitle(
                title = "Other"
            )


            SettingsItem(
                icon = Icons.Default.Help,
                title = "Help & Support",
                subtitle =
                    "Get help with SafePath AI",
                onClick = {
                    // Coming later
                }
            )


            SettingsItem(
                icon = Icons.Default.Info,
                title = "About SafePath AI",
                subtitle =
                    "Version 1.0",
                onClick = {
                    // Coming later
                }
            )


            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}


@Composable
private fun SettingsSectionTitle(
    title: String
) {

    Text(
        text = title,
        color = Color(0xFF3B82F6),
        style =
            MaterialTheme.typography.titleMedium,
        modifier =
            Modifier.padding(
                vertical = 8.dp
            )
    )
}


@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                vertical = 14.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF3B82F6),
            modifier = Modifier.size(26.dp)
        )


        Spacer(
            modifier =
                Modifier.width(16.dp)
        )


        Column(
            modifier =
                Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = Color.White
            )

            Text(
                text = subtitle,
                color = Color.Gray,
                style =
                    MaterialTheme.typography.bodySmall
            )
        }


        Icon(
            imageVector =
                Icons.Default.ChevronRight,

            contentDescription =
                null,

            tint =
                Color.Gray
        )
    }
}


@Composable
private fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF3B82F6),
            modifier = Modifier.size(26.dp)
        )


        Spacer(
            modifier =
                Modifier.width(16.dp)
        )


        Column(
            modifier =
                Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = Color.White
            )

            Text(
                text = subtitle,
                color = Color.Gray,
                style =
                    MaterialTheme.typography.bodySmall
            )
        }


        Switch(
            checked = checked,
            onCheckedChange =
                onCheckedChange
        )
    }
}