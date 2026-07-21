package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight


@Composable
fun GuardianSafetyCenter() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0x223CFF88),
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
                imageVector = Icons.Outlined.Security,
                contentDescription = null,
                tint = Color(0xFF4CFF88),
                modifier = Modifier.size(34.dp)
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Text(
                text = "Guardian Safety Center",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(
            modifier = Modifier.height(18.dp)
        )


        Text(
            text = "Guardian Connection",
            color = Color.Gray,
            fontSize = 13.sp
        )


        Text(
            text = "Connected & Monitoring",
            color = Color(0xFF4CFF88),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.Phone,
                contentDescription = null,
                tint = Color(0xFF4DB8FF),
                modifier = Modifier.size(20.dp)
            )


            Spacer(
                modifier = Modifier.width(8.dp)
            )


            Text(
                text = "Emergency contacts ready",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}