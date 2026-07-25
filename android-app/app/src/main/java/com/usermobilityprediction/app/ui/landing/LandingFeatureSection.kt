package com.usermobilityprediction.app.ui.landing

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LandingFeatureSection() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {


        FeatureGlassCard(
            icon = Icons.Outlined.AutoGraph,
            title = "AI Mobility Prediction",
            description = "Predict future movement patterns using intelligent mobility analysis."
        )


        FeatureGlassCard(
            icon = Icons.Outlined.Security,
            title = "Guardian Monitoring",
            description = "Keep trusted contacts informed with smart safety monitoring."
        )


        FeatureGlassCard(
            icon = Icons.Outlined.LocationOn,
            title = "Safe Zone Protection",
            description = "Detect safe and unknown locations automatically."
        )


        FeatureGlassCard(
            icon = Icons.Outlined.Warning,
            title = "Emergency Alerts",
            description = "Send instant alerts when unusual movement is detected."
        )
    }
}