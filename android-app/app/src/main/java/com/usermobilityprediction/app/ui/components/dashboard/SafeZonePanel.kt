package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Icon
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
fun GuardianSafetyCard(
    guardianName: String?,
    monitoringActive: Boolean?
) {


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
                shape = RoundedCornerShape(30.dp)
            )
            .padding(22.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                imageVector = Icons.Outlined.Group,
                contentDescription = null,
                tint = Color(0xFF4DB8FF),
                modifier = Modifier.size(34.dp)
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Text(
                text = "Guardian Monitoring",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }



        Spacer(
            modifier = Modifier.height(18.dp)
        )


        Text(
            text = guardianName ?: "No guardian connected",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )



        Spacer(
            modifier = Modifier.height(12.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                imageVector = Icons.Outlined.NotificationsActive,
                contentDescription = null,
                tint =
                    if (monitoringActive == true)
                        Color(0xFF4CFF88)
                    else
                        Color.Gray,
                modifier = Modifier.size(20.dp)
            )


            Spacer(
                modifier = Modifier.width(8.dp)
            )


            Text(
                text =
                    if (monitoringActive == true)
                        "Guardian alerts active"
                    else
                        "Waiting for guardian setup",
                color =
                    if (monitoringActive == true)
                        Color(0xFF4CFF88)
                    else
                        Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}