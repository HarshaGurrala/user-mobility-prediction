package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
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
import com.usermobilityprediction.app.viewmodel.ProfileState
import com.usermobilityprediction.app.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel()
) {

    val state by profileViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        when (val profileState = state) {

            is ProfileState.Loading -> {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ProfileState.Error -> {

                Text(
                    text = profileState.message,
                    color = Color.Red
                )
            }

            is ProfileState.Success -> {

                val user = profileState.profile

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color(0x33219BFF),
                                    Color(0x11111111)
                                )
                            ),
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(24.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Color(0xFF4DB8FF),
                            modifier = Modifier.size(60.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {

                            Text(
                                text = user.fullName,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = user.email,
                                color = Color(0xFF4CFF88),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                ProfileOption(
                    icon = Icons.Outlined.Email,
                    title = "Phone",
                    subtitle = user.phoneNumber ?: "Not Available"
                )

                ProfileOption(
                    icon = Icons.Outlined.Person,
                    title = "Role",
                    subtitle = user.role
                )

                ProfileOption(
                    icon = Icons.Outlined.Security,
                    title = "Safe Path ID",
                    subtitle = user.safePathId
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Logout implementation next
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x22FF4444)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {

            Icon(
                imageVector = Icons.Outlined.Logout,
                contentDescription = null,
                tint = Color(0xFFFF5555)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Logout",
                color = Color(0xFFFF5555)
            )
        }
    }
}

@Composable
private fun ProfileOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0x15151515),
                RoundedCornerShape(24.dp)
            )
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4DB8FF)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text = title,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}