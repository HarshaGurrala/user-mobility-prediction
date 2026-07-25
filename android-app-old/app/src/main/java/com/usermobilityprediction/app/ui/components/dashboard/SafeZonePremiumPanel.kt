package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationCity
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
fun SafeZonePremiumPanel() {

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
                shape = RoundedCornerShape(28.dp)
            )
            .padding(22.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.LocationCity,
                contentDescription = null,
                tint = Color(0xFF4CFF88),
                modifier = Modifier.size(30.dp)
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Text(
                text = "Safe Zone Protection",
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(
            modifier = Modifier.height(18.dp)
        )


        Text(
            text = "Current Status",
            color = Color.Gray,
            fontSize = 13.sp
        )


        Text(
            text = "User is inside safe area",
            color = Color(0xFF4CFF88),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        Text(
            text = "Safety Score 98%",
            color = Color.White,
            fontSize = 15.sp
        )
    }
}