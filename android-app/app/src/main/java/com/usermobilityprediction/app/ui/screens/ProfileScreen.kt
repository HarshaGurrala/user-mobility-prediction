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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.data.storage.TokenManager
import com.usermobilityprediction.app.navigation.Routes
import com.usermobilityprediction.app.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {

    val context = LocalContext.current

    val tokenManager = TokenManager(
        context.applicationContext
    )

    val user by profileViewModel.user.collectAsState()

    val loading by profileViewModel.loading.collectAsState()

    val error by profileViewModel.error.collectAsState()

    LaunchedEffect(Unit) {

        profileViewModel.loadCurrentUser()

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
                .padding(20.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            // =========================
            // HEADER
            // =========================

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "My Profile",

                        style =
                            MaterialTheme.typography.headlineMedium,

                        color = Color.White
                    )

                    Text(
                        text =
                            "Manage your SafePath AI account",

                        color = Color.Gray
                    )
                }

                Icon(
                    imageVector =
                        Icons.Default.Settings,

                    contentDescription =
                        "Settings",

                    tint =
                        Color(0xFF3B82F6),

                    modifier =
                        Modifier.size(28.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.height(30.dp)
            )

            // =========================
            // LOADING
            // =========================

            if (loading) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color =
                            Color(0xFF3B82F6)
                    )
                }
            }

            // =========================
            // ERROR
            // =========================

            if (error != null) {

                Text(
                    text =
                        error
                            ?: "Unable to load profile",

                    color =
                        Color.Red,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 12.dp
                            )
                )
            }

            // =========================
            // PROFILE HEADER CARD
            // =========================

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(24.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White.copy(
                                alpha = 0.06f
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

                    Box(
                        modifier =
                            Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(
                                    Color(0xFF3B82F6)
                                        .copy(
                                            alpha = 0.2f
                                        )
                                ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Person,

                            contentDescription =
                                "Profile",

                            tint =
                                Color(0xFF3B82F6),

                            modifier =
                                Modifier.size(50.dp)
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Text(
                        text =
                            user?.full_name
                                ?: "Loading...",

                        style =
                            MaterialTheme.typography.titleLarge,

                        color =
                            Color.White
                    )

                    Text(
                        text =
                            user?.role
                                ?: "USER",

                        color =
                            Color(0xFF3B82F6)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            user?.safe_path_id
                                ?.let {
                                    "SafePath ID: $it"
                                }
                                ?: "SafePath ID: Loading...",

                        color =
                            Color.Gray
                    )

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    TextButton(
                        onClick = {

                            navController.navigate(
                                Routes.EDIT_PROFILE
                            )
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Edit,

                            contentDescription =
                                null
                        )

                        Spacer(
                            modifier =
                                Modifier.size(6.dp)
                        )

                        Text(
                            text =
                                "Edit Profile"
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            // =========================
            // PERSONAL INFORMATION
            // =========================

            Text(
                text =
                    "Personal Information",

                style =
                    MaterialTheme.typography.titleMedium,

                color =
                    Color.White
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            ProfileInfoCard(
                icon =
                    Icons.Default.Person,

                title =
                    "Full Name",

                value =
                    user?.full_name
                        ?: "Loading..."
            )

            ProfileInfoCard(
                icon =
                    Icons.Default.Email,

                title =
                    "Email",

                value =
                    user?.email
                        ?: "Loading..."
            )

            ProfileInfoCard(
                icon =
                    Icons.Default.Phone,

                title =
                    "Phone Number",

                value =
                    user?.phone_number
                        ?: "Not added"
            )

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            // =========================
            // SAFETY OVERVIEW
            // =========================

            Text(
                text =
                    "Safety Overview",

                style =
                    MaterialTheme.typography.titleMedium,

                color =
                    Color.White
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            ProfileInfoCard(
                icon =
                    Icons.Default.Shield,

                title =
                    "Safety Status",

                value =
                    "Active"
            )

            ProfileInfoCard(
                icon =
                    Icons.Default.Security,

                title =
                    "Location Tracking",

                value =
                    "Enabled"
            )

            ProfileInfoCard(
                icon =
                    Icons.Default.Person,

                title =
                    "Guardians",

                value =
                    "0 Connected"
            )

            ProfileInfoCard(
                icon =
                    Icons.Default.Shield,

                title =
                    "Emergency Contacts",

                value =
                    "0 Added"
            )

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            // =========================
            // SETTINGS BUTTON
            // =========================

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

                TextButton(
                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {

                        navController.navigate(
                            Routes.SETTINGS
                        )
                    }
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Settings,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFF3B82F6)
                    )

                    Spacer(
                        modifier =
                            Modifier.size(10.dp)
                    )

                    Text(
                        text =
                            "Settings",

                        color =
                            Color.White
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            // =========================
            // LOGOUT
            // =========================

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(18.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.Red.copy(
                                alpha = 0.08f
                            )
                    )
            ) {

                TextButton(
                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {

                        // Clear JWT token
                        tokenManager.clearToken()

                        // Navigate to Login
                        // and clear navigation stack
                        navController.navigate(
                            Routes.LOGIN
                        ) {

                            popUpTo(0) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Logout,

                        contentDescription =
                            null,

                        tint =
                            Color.Red
                    )

                    Spacer(
                        modifier =
                            Modifier.size(10.dp)
                    )

                    Text(
                        text =
                            "Logout",

                        color =
                            Color.Red
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(40.dp)
            )
        }
    }
}


// =====================================
// PROFILE INFO CARD
// =====================================

@Composable
private fun ProfileInfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,

    title: String,

    value: String
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 5.dp
                ),

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White.copy(
                        alpha = 0.04f
                    )
            )
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector =
                    icon,

                contentDescription =
                    null,

                tint =
                    Color(0xFF3B82F6),

                modifier =
                    Modifier.size(24.dp)
            )

            Spacer(
                modifier =
                    Modifier.size(16.dp)
            )

            Column {

                Text(
                    text =
                        title,

                    color =
                        Color.Gray
                )

                Text(
                    text =
                        value,

                    color =
                        Color.White
                )
            }
        }
    }
}