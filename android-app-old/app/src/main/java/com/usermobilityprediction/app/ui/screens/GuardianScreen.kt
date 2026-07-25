package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usermobilityprediction.app.viewmodel.GuardianViewModel

@Composable
fun GuardianScreen() {

    val vm: GuardianViewModel = viewModel()

    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()
    val success by vm.success.collectAsState()

    var safePathId by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Connect Child",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = safePathId,
            onValueChange = {
                safePathId = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Safe Path ID")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading,
            onClick = {

                if (safePathId.isNotBlank()) {

                    vm.connectGuardian(
                        safePathId = safePathId
                    )

                }

            }
        ) {

            if (loading) {

                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )

            } else {

                Text("Send Request")

            }

        }

        success?.let {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary
            )

        }

        error?.let {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )

        }

    }

}