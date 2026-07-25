package com.usermobilityprediction.app.ui.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HeroContent(
    navController: NavController
) {

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically { it / 3 }
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Predict",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                buildAnnotatedString {

                    withStyle(
                        SpanStyle(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color.White,
                                    Color(0xFF60A5FA)
                                )
                            )
                        )
                    ) {
                        append("Every Journey")
                    }

                },
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Intelligent real-time mobility prediction powered by AI, location analytics and guardian safety monitoring.",
                color = Color.LightGray,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                StatItem(
                    "97%",
                    "Prediction\nAccuracy"
                )

                StatItem(
                    "24/7",
                    "Guardian\nMonitoring"
                )

            }

        }

    }

}

@Composable
private fun StatItem(
    value: String,
    title: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            value,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Text(
            title,
            color = Color.Gray,
            fontSize = 13.sp
        )

    }

}