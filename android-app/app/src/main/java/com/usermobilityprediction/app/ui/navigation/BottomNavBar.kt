package com.usermobilityprediction.app.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import com.usermobilityprediction.app.ui.theme.Background
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)

@Composable
fun BottomNavBar(selectedRoute: String, onTabSelected: (String) -> Unit, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem("dashboard", Icons.Default.Home, "Home"),
        BottomNavItem("tracking", Icons.Default.LocationOn, "Tracking"),
        BottomNavItem("profile", Icons.Default.Person, "Profile")
    )
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        items.forEach { item ->
            NavigationBarItem(selected = selectedRoute == item.route, onClick = { onTabSelected(item.route) }, icon = { Icon(item.icon, contentDescription = item.label) }, label = { Text(item.label) })
        }
    }
}
