package com.usermobilityprediction.app.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileHeaderCard(
    fullName: String?,
    email: String?,
    verified: Boolean?,
    lastSync: String?
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null,
                tint = Color(0xFF4DB8FF),
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = fullName ?: "Not available",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = email ?: "Not available",
            color = Color.Gray,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (verified == true) {

                Icon(
                    imageVector = Icons.Outlined.Verified,
                    contentDescription = null,
                    tint = Color(0xFF3DDC84),
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Verified Account",
                    color = Color(0xFF3DDC84),
                    fontSize = 14.sp
                )

            } else {

                Text(
                    text = "Verification Pending",
                    color = Color(0xFFFFB74D),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Last Sync",
            color = Color.Gray,
            fontSize = 12.sp
        )

        Text(
            text = lastSync ?: "Waiting for synchronization",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}