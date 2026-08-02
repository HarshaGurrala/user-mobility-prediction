package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.usermobilityprediction.app.viewmodel.EmergencyContactViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuardianScreen(
    navController: NavController,
    userId: Int,
    viewModel: EmergencyContactViewModel = viewModel()
) {

    val contacts by viewModel.contacts.collectAsState()


    LaunchedEffect(userId) {

        viewModel.loadContacts(userId)

    }


    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(
                        text = "Connected Guardians",
                        color = Color.White
                    )

                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )

                    }

                },

                colors = TopAppBarDefaults
                    .centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF050505)
                    )

            )

        },

        containerColor = Color(0xFF050505)

    ) { padding ->


        if (contacts.isEmpty()) {


            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),

                contentAlignment = Alignment.Center

            ) {

                Text(
                    text = "No guardians connected",
                    color = Color.Gray
                )

            }


        } else {


            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {


                items(contacts) { contact ->


                    Card(

                        modifier = Modifier
                            .fillMaxWidth(),

                        colors = CardDefaults.cardColors(

                            containerColor =
                                Color.White.copy(alpha = 0.05f)

                        )

                    ) {


                        Column(

                            modifier = Modifier
                                .padding(16.dp)

                        ) {


                            Icon(

                                imageVector = Icons.Default.Person,

                                contentDescription = null,

                                tint = Color(0xFF3B82F6)

                            )


                            HorizontalDivider(

                                modifier = Modifier
                                    .padding(vertical = 10.dp),

                                color = Color.DarkGray

                            )


                            Text(

                                text = contact.name ?: "Unknown Contact",

                                style = MaterialTheme.typography.titleMedium,

                                color = Color.White

                            )


                            Text(

                                text =
                                    "Relationship: ${contact.relationshipType ?: "Guardian"}",

                                color = Color.Gray

                            )


                            Text(

                                text = "Status : Connected",

                                color = Color.Gray

                            )


                            HorizontalDivider(

                                modifier = Modifier
                                    .padding(vertical = 10.dp),

                                color = Color.DarkGray

                            )


                            ContactInfoRow(

                                icon = Icons.Default.Phone,

                                text = contact.phoneNumber ?: "No phone number"

                            )


                            ContactInfoRow(

                                icon = Icons.Default.Email,

                                text = contact.email ?: "No email"

                            )


                        }

                    }

                }

            }

        }

    }

}



@Composable
fun ContactInfoRow(

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    text: String

) {

    androidx.compose.foundation.layout.Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),

        verticalAlignment = Alignment.CenterVertically

    ) {


        Icon(

            imageVector = icon,

            contentDescription = null,

            tint = Color.Gray

        )


        Text(

            text = text,

            modifier = Modifier
                .padding(start = 8.dp),

            color = Color.White

        )

    }

}