package com.usermobilityprediction.app.ui.landing

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeroVisual() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {

        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            CenterGlobe()
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 30.dp)
        ) {
            FloatingInfoCard(
                icon = Icons.Outlined.LocationOn,
                iconColor = Color(0xFF3B82F6),
                title = "Current Location",
                subtitle = "Hyderabad",
                delay = 0
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 1.dp, top = 5.dp)
        ) {
            FloatingInfoCard(
                icon = Icons.Outlined.AutoGraph,
                iconColor = Color(0xFF22C55E),
                title = "AI Prediction",
                subtitle = "97% Confidence",
                delay = 600
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 25.dp, bottom = 15.dp)
        ) {
            FloatingInfoCard(
                icon = Icons.Outlined.Security,
                iconColor = Color(0xFFA855F7),
                title = "Guardian",
                subtitle = "Protection Active",
                delay = 1200
            )
        }

    }

}