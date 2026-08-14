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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security

import androidx.compose.material.icons.filled.Warning

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController

import com.usermobilityprediction.app.data.location.LocationTracker
import com.usermobilityprediction.app.data.model.UserDashboardUiState
import com.usermobilityprediction.app.navigation.Routes
import com.usermobilityprediction.app.viewmodel.UserDashboardViewModel

import androidx.compose.ui.platform.LocalContext


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import com.usermobilityprediction.app.viewmodel.SOSViewModel
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material.icons.filled.WarningAmber


import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState



import androidx.compose.ui.unit.sp
import com.usermobilityprediction.app.viewmodel.NotificationViewModel


@Composable

fun UserDashboardScreen(
    navController: NavController,
    userId: Int
) {
    val sosViewModel: SOSViewModel = viewModel()

        val viewModel: UserDashboardViewModel = viewModel()

        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event: Lifecycle.Event ->

            if (event == Lifecycle.Event.ON_RESUME) {

                viewModel.refreshDashboard(
                    userId
                )

            }

        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    val dashboardViewModel: UserDashboardViewModel = viewModel()
    val uiState by dashboardViewModel.uiState.collectAsState()
    val liveLocation by LocationTracker.currentLocation.collectAsState()
    val context = LocalContext.current

    DisposableEffect(Unit) {

        val tracker = LocationTracker(context)

        tracker.startTracking()

        onDispose {

            tracker.stopTracking()

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {

        // ==================================================
        // Header
        // ==================================================

        UserDashboardHeader(
            navController = navController,
            uiState = uiState
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ==================================================
        // Safety Overview
        // ==================================================

        SafetyOverviewCard(
            uiState = uiState
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // Current Location
        // ==================================================

        CurrentLocationCard(
            uiState = uiState,
            liveLocation = liveLocation
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // Safe Zone + Guardians
        // ==================================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SafeZoneCard(
                modifier = Modifier.weight(1f),
                uiState = uiState
            )

            GuardianStatusCard(
                modifier = Modifier.weight(1f),
                uiState = uiState,
                onClick = {
                    navController.navigate(Routes.PROFILE)
                }
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // AI Mobility Prediction
        // ==================================================

        PredictionCard(
            uiState = uiState
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )





        // ==================================================
        // Recent Safety Activity
        // ==================================================

        RecentActivityCard(
            uiState = uiState
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )



//        EmergencyContactsCard(
//            uiState = uiState
//        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==================================================
        // Emergency / SOS
        // ==================================================


        EmergencyCard(
            userId = userId,
            currentLocation = liveLocation,
            viewModel = sosViewModel
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )


        SafetyNotificationCard(
            notifications = uiState.notifications
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


    }
}
@Composable
private fun EmergencyCard(
    userId: Int,
    currentLocation: android.location.Location?,
    viewModel: SOSViewModel
) {

    val loading by viewModel.loading.collectAsState()
    val message by viewModel.message.collectAsState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7F1D1D)
        ),
        onClick = {

            if (currentLocation != null && !loading) {

                viewModel.triggerSOS(
                    latitude = currentLocation.latitude,
                    longitude = currentLocation.longitude
                )

            }

        }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                if (loading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )

                } else {

                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Emergency",
                        tint = Color.White
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Emergency / SOS",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = when {
                        loading -> "Sending emergency alert..."
                        message != null -> message!!
                        currentLocation == null ->
                            "Waiting for current location..."
                        else ->
                            "Send emergency alert to your contacts"
                    },
                    color = Color.White.copy(alpha = 0.75f),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}



// ==========================================================
// Header
// ==========================================================
@Composable
private fun UserDashboardHeader(
    navController: NavController,
    uiState: UserDashboardUiState
) {

    val notificationViewModel: NotificationViewModel = viewModel()

    val notifications by notificationViewModel.notifications.collectAsState()

    val unreadCount = notifications.count {
        it.status.equals(
            "unread",
            ignoreCase = true
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME) {
                notificationViewModel.loadNotifications()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "Welcome ${uiState.userName} 👋",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Your safety is our priority.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        // ==================================================
        // NOTIFICATION BELL + UNREAD BADGE
        // ==================================================

        Box(
            contentAlignment = Alignment.TopEnd
        ) {

            IconButton(
                onClick = {
                    navController.navigate("notifications")
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            if (unreadCount > 0) {

                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(
                            color = Color(0xFFEF4444),
                            shape = RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = if (unreadCount > 9) {
                            "9+"
                        } else {
                            unreadCount.toString()
                        },
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
// ==========================================================
// Safety Overview
// ==========================================================

@Composable
private fun SafetyOverviewCard(
    uiState: UserDashboardUiState
) {

    val safetyColor = when (uiState.safetyStatus) {
        "SAFE" -> Color(0xFF4CAF50)
        "WARNING" -> Color(0xFFFF9800)
        "UNKNOWN" -> Color(0xFFF44336)
        else -> Color.White
    }

    val safetyIcon = when (uiState.safetyStatus) {
        "SAFE" -> Icons.Default.Security
        "WARNING" -> Icons.Default.Warning
        "UNKNOWN" -> Icons.Default.Error
        else -> Icons.Default.Info
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111827)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = safetyColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = safetyIcon,
                        contentDescription = null,
                        tint = safetyColor
                    )
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column {

                    Text(
                        text = "Safety Overview",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = uiState.safetyStatus,
                        color = safetyColor,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Last Event: ${uiState.lastEvent}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Last Updated: ${uiState.lastUpdated}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = uiState.safetyMessage,
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )

            if (uiState.safetyStatus == "UNKNOWN") {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = {
                        // Phase 4:
                        // Open Safety Confirmation Screen
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text("Confirm Safety")
                }
            }
        }
    }
}



// ==========================================================
// Current Location
// ==========================================================

@Composable
private fun CurrentLocationCard(
    uiState: UserDashboardUiState,
    liveLocation: android.location.Location?
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111827)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            DashboardSectionTitle(
                icon = Icons.Default.LocationOn,
                title = "Current Location"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Location tracking is active",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = uiState.currentLocation,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (liveLocation != null) {

                Text(
                    text = "Latitude : ${liveLocation!!.latitude}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Longitude : ${liveLocation!!.longitude}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Accuracy : ${liveLocation!!.accuracy} m",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

            } else {

                Text(
                    text = "Waiting for GPS...",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            }
            }
        }




// ==========================================================
// Safe Zone
// ==========================================================

@Composable
private fun SafeZoneCard(
    modifier: Modifier = Modifier,
    uiState: UserDashboardUiState
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111827)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color(0xFF60A5FA)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Safe Zone",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = uiState.safeZone,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = uiState.safetyMessage,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}




// ==========================================================
// Guardian Status
// ==========================================================

@Composable
private fun GuardianStatusCard(
    modifier: Modifier = Modifier,
    uiState: UserDashboardUiState,
    onClick: () -> Unit
){

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111827)
        ),
        onClick = onClick
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color(0xFFA78BFA)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Guardians",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = uiState.guardianStatus,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Guardian connection status",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


// ==========================================================
// AI Prediction
// ==========================================================

@Composable
private fun PredictionCard(
    uiState: UserDashboardUiState
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111827)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            DashboardSectionTitle(
                icon = Icons.Default.ArrowForward,
                title = "AI Mobility Prediction"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = uiState.prediction,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = uiState.safetyMessage,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Confidence: ${uiState.confidence}",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


// ==========================================================
// Recent Activity
// ==========================================================

@Composable
private fun RecentActivityCard(
    uiState: UserDashboardUiState
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111827)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            DashboardSectionTitle(
                icon = Icons.Default.Notifications,
                title = "Recent Safety Activity"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = uiState.recentAlert,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = uiState.safetyMessage,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


// ==========================================================
// Reusable Section Title
// ==========================================================

@Composable
private fun DashboardSectionTitle(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF60A5FA),
            modifier = Modifier.size(24.dp)
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Composable
//private fun EmergencyContactsCard(
//    uiState: UserDashboardUiState
//) {
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(24.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF111827)
//        )
//    ) {
//
//        Column(
//            modifier = Modifier.padding(20.dp)
//        ) {
//
//            DashboardSectionTitle(
//                icon = Icons.Default.Phone,
//                title = "Emergency Contacts"
//            )
//
//
//            Spacer(
//                modifier = Modifier.height(16.dp)
//            )
//
//
//            if(uiState.emergencyContacts.isEmpty()) {
//
//                Text(
//                    text = "No emergency contacts added.",
//                    color = Color.Gray,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//
//            } else {
//
//
//                uiState.emergencyContacts.forEach { contact ->
//
//
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                vertical = 6.dp
//                            ),
//
//                        shape = RoundedCornerShape(16.dp),
//
//                        colors = CardDefaults.cardColors(
//                            containerColor = Color(0xFF1F2937)
//                        )
//                    ) {
//
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(14.dp),
//
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//
//
//                            Box(
//                                modifier = Modifier
//                                    .size(42.dp)
//                                    .background(
//                                        Color(0xFF2563EB)
//                                            .copy(alpha = 0.2f),
//                                        RoundedCornerShape(14.dp)
//                                    ),
//
//                                contentAlignment = Alignment.Center
//                            ) {
//
//                                Icon(
//                                    imageVector = Icons.Default.Person,
//                                    contentDescription = null,
//                                    tint = Color(0xFF60A5FA)
//                                )
//                            }
//
//
//                            Spacer(
//                                modifier = Modifier.width(12.dp)
//                            )
//
////
////                            Column {
////
////                                Text(
////                                    text = contact.name ?: "Unknown Contact",
////                                    color = Color.White,
////                                    fontWeight = FontWeight.Bold
////                                )
////
////                                Text(
////                                    text = contact.relationshipType ?: "Contact",
////                                    color = Color.Gray,
////                                    style = MaterialTheme.typography.bodySmall
////                                )
////
////                                Text(
////                                    text = if (!contact.phoneNumber.isNullOrBlank()) {
////                                        contact.phoneNumber
////                                    } else {
////                                        "No Phone Number"
////                                    },
////                                    color = Color.LightGray,
////                                    style = MaterialTheme.typography.bodySmall
////                                )
////                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
private fun SafetyNotificationCard(
    notifications: List<com.usermobilityprediction.app.data.model.NotificationResponse>
) {

    val unreadNotifications =
        notifications.filter {
            it.status.lowercase() == "unread"
        }

    if (unreadNotifications.isEmpty()) {
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        unreadNotifications.forEach { notification ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF78350F)
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    Color(0xFFF59E0B).copy(alpha = 0.2f),
                                    RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Safety Alert",
                                tint = Color(0xFFFBBF24)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "Safety Alert",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = notification.title,
                                color = Color.White.copy(alpha = 0.9f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = notification.message,
                        color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}