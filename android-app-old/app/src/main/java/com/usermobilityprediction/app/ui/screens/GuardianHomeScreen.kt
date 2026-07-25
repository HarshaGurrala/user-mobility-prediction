package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usermobilityprediction.app.viewmodel.GuardianViewModel

@Composable
fun GuardianHomeScreen() {

    val vm: GuardianViewModel = viewModel()

    val users by vm.connectedUsers.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadConnectedUsers()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(users) { user ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(user.email)

                    Spacer(Modifier.height(6.dp))

                    Text("Safe Path ID: ${user.safePathId}")

                }

            }

        }

    }

}