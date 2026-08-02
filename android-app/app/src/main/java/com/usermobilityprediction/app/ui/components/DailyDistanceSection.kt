package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.data.model.DailyDistanceResponse
import java.util.Locale

@Composable
fun DailyDistanceSection(
    dailyDistance: List<DailyDistanceResponse>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF101820),
                RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Route,
                contentDescription = null,
                tint = Color(0xFF3FA9FF)
            )


            Text(
                text = "Daily Travel",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 10.dp)
            )
        }


        Text(
            text = "Distance travelled each day",
            color = Color(0xFF71859A),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )


        if (dailyDistance.isEmpty()) {

            Text(
                text = "No daily travel data available",
                color = Color.Gray,
                modifier = Modifier.padding(top = 24.dp)
            )


        } else {


            val maxDistance =
                dailyDistance
                    .maxOfOrNull { it.distance }
                    ?.coerceAtLeast(0.1)
                    ?: 0.1


            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .padding(top = 24.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(10.dp),

                verticalAlignment =
                    Alignment.Bottom
            ) {


                dailyDistance.forEach { item ->


                    val distance =
                        item.distance
                            .coerceAtLeast(0.0)


                    val barHeight =
                        if(distance > 0)
                            ((distance / maxDistance) * 120)
                                .coerceAtLeast(8.0)
                        else
                            5.0



                    Column(

                        modifier =
                            Modifier.weight(1f),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {



                        Text(

                            text =
                                formatDistance(distance),

                            color =
                                Color(0xFFB7C7D8),

                            style =
                                MaterialTheme.typography.labelSmall,

                            maxLines = 1,

                            textAlign =
                                TextAlign.Center

                        )



                        Box(

                            modifier =
                                Modifier
                                    .padding(
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                                    .width(22.dp)
                                    .height(
                                        barHeight.dp
                                    )
                                    .background(
                                        Color(0xFF1688FF),
                                        RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp
                                        )
                                    )

                        )



                        Text(

                            text =
                                formatDate(item.date),

                            color =
                                Color(0xFF71859A),

                            style =
                                MaterialTheme.typography.labelSmall,

                            maxLines = 1

                        )

                    }

                }

            }

        }

    }

}



private fun formatDistance(
    value: Double
): String {

    return when {

        value >= 1000 ->
            String.format(
                Locale.US,
                "%.1fk",
                value / 1000
            )

        else ->
            String.format(
                Locale.US,
                "%.1f",
                value
            )
    }
}



private fun formatDate(
    date:String
):String {

    return try {

        date.substring(5)

    } catch(e:Exception){

        date

    }

}