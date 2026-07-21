package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usermobilityprediction.app.viewmodel.HomeViewModel
import com.usermobilityprediction.app.ui.components.ErrorBadge
import com.usermobilityprediction.app.ui.components.PressableGlassCard

@Composable
fun NotificationsScreen(homeViewModel: HomeViewModel = viewModel()) {
    val notifications by homeViewModel.notifications.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    if (isLoading) {
        // skeleton shimmer
        val shimmer = com.usermobilityprediction.app.ui.animations.rememberShimmerBrush()
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(4) {
                Box(modifier = Modifier.fillMaxWidth().height(64.dp).background(shimmer, shape = RoundedCornerShape(12.dp))) {}
            }
        }
        return
    }

    if (notifications.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            ErrorBadge()
            Spacer(Modifier.height(8.dp))
            Text("No notifications", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(notifications) { n ->
            PressableGlassCard(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(n.title)
                    Text(n.body)
                    Text(n.timeAgo, style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
