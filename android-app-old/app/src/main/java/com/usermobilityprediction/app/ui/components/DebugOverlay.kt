package com.usermobilityprediction.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usermobilityprediction.app.dev.DevDebug
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavController

@Composable
fun DebugOverlay(navController: NavController, modifier: Modifier = Modifier) {
    val backStack by navController.currentBackStackEntryAsState()
    val route = backStack?.destination?.route ?: "unknown"

    Card(modifier = modifier
        .fillMaxWidth()
        .heightIn(max = 240.dp)
        .padding(8.dp),) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)) {
            Text("DEV DEBUG - route: $route", style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Column {
                    DevDebug.lines.forEach { line ->
                        Text(line, style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
                    }
                }
            }
        }
    }
}
