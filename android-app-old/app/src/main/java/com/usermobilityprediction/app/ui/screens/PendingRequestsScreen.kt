package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usermobilityprediction.app.viewmodel.GuardianViewModel

@Composable
fun PendingRequestsScreen() {

    val vm: GuardianViewModel = viewModel()

    val requests by vm.pendingRequests.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadPendingRequests()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(requests) { request ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = request.guardianName,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(request.guardianEmail)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = {
                                vm.acceptRequest(request.requestId)
                            }
                        ) {
                            Text("Accept")
                        }

                        OutlinedButton(
                            onClick = {
                                vm.rejectRequest(request.requestId)
                            }
                        ) {
                            Text("Reject")
                        }
                    }
                }
            }
        }
    }
}