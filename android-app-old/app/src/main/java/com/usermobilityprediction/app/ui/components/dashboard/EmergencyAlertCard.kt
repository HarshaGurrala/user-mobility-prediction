package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun EmergencyAlertCard(
    alertActive: Boolean,
    onEmergencyClick: () -> Unit
) {


    Column(

        modifier = Modifier
            .fillMaxWidth()
            .background(

                brush = Brush.verticalGradient(
                    listOf(
                        Color(0x44FF3333),
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

                imageVector = Icons.Outlined.Warning,

                contentDescription = null,

                tint = Color(0xFFFF5555),

                modifier = Modifier.size(34.dp)

            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Text(

                text = "Emergency Protection",

                color = Color.White,

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold

            )
        }



        Spacer(
            modifier = Modifier.height(18.dp)
        )



        Text(

            text =
                if (alertActive)
                    "Emergency alert is active"
                else
                    "Send emergency alert to guardian",

            color = Color.White,

            fontSize = 16.sp

        )



        Spacer(
            modifier = Modifier.height(18.dp)
        )



        Button(

            onClick = onEmergencyClick,

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape = RoundedCornerShape(18.dp),

            colors = ButtonDefaults.buttonColors(

                containerColor = Color(0xFFFF3333)

            )

        ) {


            Text(

                text = "Emergency Alert",

                color = Color.White,

                fontSize = 16.sp,

                fontWeight = FontWeight.Bold

            )

        }
    }
}