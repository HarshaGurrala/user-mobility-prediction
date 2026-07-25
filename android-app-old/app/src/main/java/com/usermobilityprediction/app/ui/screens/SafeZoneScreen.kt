package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usermobilityprediction.app.viewmodel.HomeViewModel

@Composable
fun SafeZoneScreen(homeViewModel: HomeViewModel = viewModel()) {
    val zones by homeViewModel.safeZones.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    if (zones.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No safe zones configured") }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(zones) { z ->
            Card(modifier = Modifier.padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(z.name)
                    Text("Radius: ${z.radiusMeters}m")
                }
            }
        }
    }
}
