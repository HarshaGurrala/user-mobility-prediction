package com.usermobilityprediction.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.usermobilityprediction.app.data.storage.TokenManager

@Composable
fun SplashScreen(navController: NavController, tokenManager: TokenManager) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }

    LaunchedEffect(Unit) {
        val token = tokenManager.getToken()
        if (token != null) {
            // navigate into the full app shell (bottom navigation)
            navController.navigate("app") { popUpTo("splash") { inclusive = true } }
        } else {
            navController.navigate("landing") { popUpTo("splash") { inclusive = true } }
        }
    }
}
