package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.Person
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
fun ProfileStatusCard() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0x33219BFF),
                        Color(0x11111111)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            tint = Color(0xFF4DB8FF),
            modifier = Modifier.size(42.dp)
        )


        Spacer(
            modifier = Modifier.width(16.dp)
        )


        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "User Account",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )


            Text(
                text = "AI Monitoring Active",
                color = Color(0xFF4CFF88),
                fontSize = 13.sp
            )
        }


        Icon(
            imageVector = Icons.Outlined.VerifiedUser,
            contentDescription = null,
            tint = Color(0xFF4CFF88),
            modifier = Modifier.size(32.dp)
        )
    }
}