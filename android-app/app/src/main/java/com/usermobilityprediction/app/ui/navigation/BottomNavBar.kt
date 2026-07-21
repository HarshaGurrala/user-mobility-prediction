package com.usermobilityprediction.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun BottomNavBar(
    selectedRoute: String,
    onTabSelected: (String) -> Unit
) {

    NavigationBar(
        containerColor = Color(0xFF080808)
    ) {


        NavigationBarItem(

            selected = selectedRoute == "landing",

            onClick = {
                onTabSelected("landing")
            },

            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },

            label = {
                Text("Home")
            }
        )



        NavigationBarItem(

            selected = selectedRoute == "dashboard",

            onClick = {
                onTabSelected("dashboard")
            },

            icon = {
                Icon(
                    Icons.Outlined.Dashboard,
                    contentDescription = "Dashboard"
                )
            },

            label = {
                Text("Dashboard")
            }
        )



        NavigationBarItem(

            selected = selectedRoute == "analytics",

            onClick = {
                onTabSelected("analytics")
            },

            icon = {
                Icon(
                    Icons.Outlined.Analytics,
                    contentDescription = "Analytics"
                )
            },

            label = {
                Text("Analytics")
            }
        )



        NavigationBarItem(

            selected = selectedRoute == "profile",

            onClick = {
                onTabSelected("profile")
            },

            icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "Profile"
                )
            },

            label = {
                Text("Profile")
            }
        )
    }
}