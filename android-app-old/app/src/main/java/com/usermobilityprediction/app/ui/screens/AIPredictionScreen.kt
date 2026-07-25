package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxWidth
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
fun AIPredictionScreen(homeViewModel: HomeViewModel = viewModel()) {
    val predictions by homeViewModel.predictions.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    if (predictions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No AI predictions yet") }
        return
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(predictions) { p ->
            Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(p.placeName)
                    Text("Confidence ${p.confidence}% • ETA ${p.eta}")
                }
            }
        }
    }
}
