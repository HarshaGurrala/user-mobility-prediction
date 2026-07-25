package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AboutScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("About — User Mobility Prediction") } }
@Composable
fun HelpScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Help & Support") } }
@Composable
fun PrivacyScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Privacy Policy") } }
@Composable
fun TermsScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Terms & Conditions") } }
