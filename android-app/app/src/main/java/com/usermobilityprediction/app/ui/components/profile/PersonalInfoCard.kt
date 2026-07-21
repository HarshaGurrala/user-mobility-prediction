package com.usermobilityprediction.app.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PersonalInfoCard(
    fullName: String?,
    email: String?,
    phone: String?,
    userId: String?,
    memberSince: String?
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0x33219BFF),
                        Color(0x11000000)
                    )
                ),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(22.dp)
    ) {

        Text(
            text = "Personal Information",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        ProfileInfoRow(
            icon = Icons.Outlined.Person,
            title = "Full Name",
            value = fullName
        )

        DividerSpace()

        ProfileInfoRow(
            icon = Icons.Outlined.Email,
            title = "Email",
            value = email
        )

        DividerSpace()

        ProfileInfoRow(
            icon = Icons.Outlined.Call,
            title = "Phone",
            value = phone
        )

        DividerSpace()

        ProfileInfoRow(
            icon = Icons.Outlined.Badge,
            title = "User ID",
            value = userId
        )

        DividerSpace()

        ProfileInfoRow(
            icon = Icons.Outlined.CalendarMonth,
            title = "Member Since",
            value = memberSince
        )
    }
}

@Composable
private fun ProfileInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String?
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4DB8FF),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value ?: "Not available",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun DividerSpace() {

    Spacer(modifier = Modifier.height(16.dp))

    HorizontalDivider(
        color = Color.White.copy(alpha = 0.08f)
    )

    Spacer(modifier = Modifier.height(16.dp))
}