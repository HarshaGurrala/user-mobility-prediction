//package com.usermobilityprediction.app.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//
//import com.usermobilityprediction.app.data.model.EmergencyContactCreateRequest
//import com.usermobilityprediction.app.viewmodel.EmergencyContactViewModel
//
//
//@Composable
//fun EditEmergencyContactScreen(
//
//    navController: NavController,
//
//    contactId: Int,
//
//    userId: Int,
//
//    name: String,
//
//    relationship: String,
//
//    phone: String,
//
//    email: String,
//
//    viewModel: EmergencyContactViewModel = viewModel()
//
//) {
//
//
//    var contactName by remember {
//        mutableStateOf(name)
//    }
//
//
//    var contactRelationship by remember {
//        mutableStateOf(relationship)
//    }
//
//
//    var contactPhone by remember {
//        mutableStateOf(phone)
//    }
//
//
//    var contactEmail by remember {
//        mutableStateOf(email)
//    }
//
//
//    val error by viewModel.error.collectAsState()
//
//
//
//    Column(
//
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF050505))
//            .verticalScroll(
//                rememberScrollState()
//            )
//            .padding(20.dp)
//
//    ) {
//
//
//        IconButton(
//
//            onClick = {
//
//                navController.popBackStack()
//
//            }
//
//        ) {
//
//            Icon(
//
//                imageVector = Icons.Default.ArrowBack,
//
//                contentDescription = null,
//
//                tint = Color.White
//
//            )
//
//        }
//
//
//
//        Text(
//
//            text = "Edit Emergency Contact",
//
//            style = MaterialTheme.typography.headlineMedium,
//
//            color = Color.White
//
//        )
//
//
//        Spacer(
//            modifier = Modifier.height(24.dp)
//        )
//
//
//
//        OutlinedTextField(
//
//            value = contactName,
//
//            onValueChange = {
//                contactName = it
//            },
//
//            label = {
//                Text("Name")
//            },
//
//            modifier = Modifier.fillMaxWidth()
//
//        )
//
//
//        Spacer(
//            modifier = Modifier.height(16.dp)
//        )
//
//
//        OutlinedTextField(
//
//            value = contactRelationship,
//
//            onValueChange = {
//                contactRelationship = it
//            },
//
//            label = {
//                Text("Relationship")
//            },
//
//            modifier = Modifier.fillMaxWidth()
//
//        )
//
//
//
//        Spacer(
//            modifier = Modifier.height(16.dp)
//        )
//
//
//        OutlinedTextField(
//
//            value = contactPhone,
//
//            onValueChange = {
//                contactPhone = it
//            },
//
//            label = {
//                Text("Phone Number")
//            },
//
//            modifier = Modifier.fillMaxWidth()
//
//        )
//
//
//        Spacer(
//            modifier = Modifier.height(16.dp)
//        )
//
//
//        OutlinedTextField(
//
//            value = contactEmail,
//
//            onValueChange = {
//                contactEmail = it
//            },
//
//            label = {
//                Text("Email")
//            },
//
//            modifier = Modifier.fillMaxWidth()
//
//        )
//
//
//
//        Spacer(
//            modifier = Modifier.height(24.dp)
//        )
//
//
//
//        if (error != null) {
//
//            Text(
//
//                text = error ?: "",
//
//                color = Color.Red
//
//            )
//
//            Spacer(
//                modifier = Modifier.height(10.dp)
//            )
//
//        }
//
//
//
//        Button(
//
//            modifier = Modifier.fillMaxWidth(),
//
//            onClick = {
//
//
//                viewModel.updateContact(
//
//                    contactId = contactId,
//
//                    userId = userId,
//
//                    request = EmergencyContactCreateRequest(
//
//                        name = contactName,
//
//                        relationshipType = contactRelationship,
//
//                        phoneNumber = contactPhone,
//
//                        email =
//                            if(contactEmail.isBlank())
//                                null
//                            else
//                                contactEmail
//
//                    )
//
//                ) {
//
//
//                    navController.popBackStack()
//
//                }
//
//
//            }
//
//        ) {
//
//
//            Text(
//                "Update Contact"
//            )
//
//
//        }
//
//
//    }
//
//}