package com.usermobilityprediction.app.ui.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Navigation
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CurrentLocationIntelligenceCard(
    locationName: String?,
    latitude: Double?,
    longitude: Double?,
    timestamp: Long?
) {


    val updatedTime =
        timestamp?.let {

            SimpleDateFormat(
                "hh:mm:ss a",
                Locale.getDefault()
            ).format(
                Date(it)
            )

        } ?: "--"



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
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = Color(0xFF4DB8FF),
                modifier = Modifier.size(34.dp)
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Text(
                text = "Current Location",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }



        Spacer(
            modifier = Modifier.height(20.dp)
        )


        Text(
            text = locationName ?: "Unknown Location",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )



        Spacer(
            modifier = Modifier.height(12.dp)
        )


        Text(
            text =
                if (latitude != null && longitude != null) {

                    "Latitude: %.6f\nLongitude: %.6f"
                        .format(
                            latitude,
                            longitude
                        )

                } else {

                    "Waiting for GPS..."

                },
            color = Color.LightGray,
            fontSize = 15.sp
        )



        Spacer(
            modifier = Modifier.height(18.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Outlined.Navigation,
                    contentDescription = null,
                    tint = Color(0xFF4CFF88),
                    modifier = Modifier.size(18.dp)
                )


                Spacer(
                    modifier = Modifier.width(6.dp)
                )


                Text(
                    text = "Real GPS",
                    color = Color(0xFF4CFF88),
                    fontSize = 13.sp
                )
            }



            Text(
                text = updatedTime,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}