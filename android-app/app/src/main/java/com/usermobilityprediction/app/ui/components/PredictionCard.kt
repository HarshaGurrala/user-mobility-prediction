package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.usermobilityprediction.app.data.model.PredictionResponse

@Composable
fun PredictionCard(
    prediction: PredictionResponse?
){


    val confidence =
        prediction?.confidence
            ?.coerceIn(0.0,99.9)
            ?: 0.0


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(28.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color(0xFF111111)
            )

    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(22.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {


            /*
             * HEADER
             */

            Row(

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                Icon(

                    imageVector =
                        Icons.Default.Psychology,

                    contentDescription =
                        null,

                    tint =
                        Color(0xFF42A5F5)

                )


                Text(

                    text =
                        "AI Next Destination",

                    color =
                        Color.White,

                    fontSize =
                        22.sp,

                    modifier =
                        Modifier.padding(
                            start = 10.dp
                        )

                )

            }



            /*
             * LOCATION
             */

            Column {


                Text(

                    text =
                        "Predicted Location",

                    color =
                        Color(0xFF8FA4B8)

                )


                Row(

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Icon(

                        imageVector =
                            Icons.Default.LocationOn,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFF42A5F5)

                    )


                    Text(

                        text =
                            prediction?.location
                                ?: "No prediction available",

                        color =
                            Color.White,

                        modifier =
                            Modifier.padding(
                                start = 8.dp
                            )

                    )

                }

            }



            /*
             * CONFIDENCE
             */

            Column {


                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween

                ) {


                    Text(

                        text =
                            "Confidence",

                        color =
                            Color(0xFFB7C7D8)

                    )


                    Text(

                        text =
                            String.format(
                                "%.1f%%",
                                confidence
                            ),

                        color =
                            Color.White

                    )

                }



                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .height(10.dp)
                            .background(
                                Color(0xFF252525),
                                RoundedCornerShape(10.dp)
                            )

                ) {


                    Row(

                        modifier =
                            Modifier
                                .fillMaxWidth(
                                    (
                                            confidence / 100
                                            )
                                        .toFloat()
                                )
                                .height(10.dp)
                                .background(
                                    Color(0xFF42A5F5),
                                    RoundedCornerShape(10.dp)
                                )

                    ) {}

                }

            }



            /*
             * MATCH STATUS
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {


                Text(

                    text =
                        "Prediction Status",

                    color =
                        Color(0xFF8FA4B8)

                )


                Text(

                    text =
                        if(
                            prediction?.matched == true
                        )
                            "MATCHED"
                        else
                            "PENDING",

                    color =
                        if(
                            prediction?.matched == true
                        )
                            Color(0xFF20C878)
                        else
                            Color(0xFFFFB020)

                )

            }



            /*
             * ACCURACY
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {


                Text(

                    text =
                        "Accuracy Distance",

                    color =
                        Color(0xFF8FA4B8)

                )


                Text(

                    text =
                        if(
                            prediction?.predictionAccuracy != null
                        )
                            "${prediction.predictionAccuracy} m"
                        else
                            "--",

                    color =
                        Color.White

                )

            }

        }

    }

}