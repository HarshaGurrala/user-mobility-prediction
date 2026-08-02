package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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


@Composable
fun DashboardPredictionCard(

    location: String,

    confidence: String,

    status: String

) {


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

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
                    .padding(20.dp),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)

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
                        20.sp,

                    modifier =
                        Modifier.padding(
                            start = 10.dp
                        )

                )


            }



            /*
             * LOCATION
             */

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
                        location,

                    color =
                        Color.White,

                    modifier =
                        Modifier.padding(
                            start = 8.dp
                        )

                )

            }




            /*
             * CONFIDENCE + STATUS
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {


                Column {


                    Text(

                        text =
                            "Confidence",

                        color =
                            Color(0xFF8FA4B8)

                    )


                    Text(

                        text =
                            confidence,

                        color =
                            Color.White

                    )


                }




                Column(

                    horizontalAlignment =
                        Alignment.End

                ) {


                    Text(

                        text =
                            "Status",

                        color =
                            Color(0xFF8FA4B8)

                    )


                    Text(

                        text =
                            status,

                        color =
                            if(
                                status == "MATCHED"
                            )
                                Color(0xFF20C878)
                            else
                                Color(0xFFFFB020)

                    )

                }


            }


        }

    }

}