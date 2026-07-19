package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.usermobilityprediction.app.ui.components.InfoCard
import com.usermobilityprediction.app.ui.components.StatusChip
import com.usermobilityprediction.app.viewmodel.HomeViewModel
import com.usermobilityprediction.app.ui.components.PrimaryGradientButton

@Composable
fun HomeDashboardScreen(homeViewModel: HomeViewModel = viewModel(), navController: NavController? = null) {
    val locations by homeViewModel.locations.collectAsState()
    val predictions by homeViewModel.predictions.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Welcome back", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.onBackground)
                StatusChip("Safe")
            }
        }

        item {
            InfoCard(title = "Live AI Prediction", subtitle = "Where you might go next")
        }

        item {
            if (predictions.isEmpty()) {
                Text("No predictions yet", style = MaterialTheme.typography.bodyLarge)
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    predictions.forEach { p ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(p.placeName, style = MaterialTheme.typography.titleLarge)
                                Text("Accuracy: ${p.confidence}% • ETA ${p.eta}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }

        item {
            InfoCard(title = "Quick Actions", subtitle = "Emergency, Safe Zone, Share")
            Spacer(Modifier.height(8.dp))
            PrimaryGradientButton(text = "Send Emergency Alert", onClick = { navController?.navigate("emergency") })
        }

        item {
            Text("Recent locations", style = MaterialTheme.typography.titleLarge)
        }

        if (locations.isEmpty()) {
            item { Text("No recent locations", style = MaterialTheme.typography.bodyLarge) }
        } else {
            items(locations) { loc ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(loc.label ?: "Location", style = MaterialTheme.typography.titleLarge)
                        Text("${loc.latitude}, ${loc.longitude}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        
    }
}
