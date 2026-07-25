package com.usermobilityprediction.app.ui.landing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LandingFooter() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Secure • AI Powered • Guardian Protected",
            color = Color(0xFF4DB8FF),
            fontSize = 13.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Your intelligent mobility safety companion",
            color = Color.Gray,
            fontSize = 12.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "© User Mobility Prediction",
            color = Color.DarkGray,
            fontSize = 11.sp
        )
    }
}