package com.usermobilityprediction.app.ui.screens
import com.usermobilityprediction.app.navigation.Routes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.foundation.layout.width

import android.content.Intent
import android.net.Uri

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Add

@Composable
fun EmergencyContactsScreen(
    navController: NavController,
    userId: Int,
    viewModel: EmergencyContactViewModel = viewModel()
) {

    val contacts by viewModel.contacts.collectAsState()

    val loading by viewModel.loading.collectAsState()

    val error by viewModel.error.collectAsState()

    LaunchedEffect(userId) {

        viewModel.loadContacts(userId)

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Icon(
                        Icons.Default.ArrowBack,
                        null,
                        tint = Color.White
                    )

                }

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Column {

                    Text(
                        text = "Emergency Contacts",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    Text(
                        text = "People to contact during emergencies",
                        color = Color.Gray
                    )

                }

            }

            when {

                loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator()

                    }

                }

                error != null -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = error ?: "Unknown error",
                            color = Color.Red
                        )

                    }

                }

                contacts.isEmpty() -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "No Emergency Contacts",
                            color = Color.Gray
                        )

                    }

                }

                else -> {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(contacts) { contact ->

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(alpha = 0.05f)
                                )
                            ) {

                                Column(
                                    modifier = Modifier.padding(18.dp)
                                ) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            tint = Color(0xFF3B82F6)
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Text(
                                            text = contact.name ?: "Unknown Contact",
                                            color = Color.White,
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Warning,
                                            contentDescription = null,
                                            tint = Color.Gray
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = contact.relationshipType ?: "Not Specified",
                                            color = Color.Gray
                                        )

                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Phone,
                                            contentDescription = null,
                                            tint = Color.Gray
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = contact.phoneNumber ?: "No Phone Number",
                                            color = Color.White
                                        )

                                    }

                                    val email = contact.email

                                    if (!email.isNullOrBlank()) {

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Icon(
                                                imageVector = Icons.Default.Email,
                                                contentDescription = null,
                                                tint = Color.Gray
                                            )

                                            Spacer(modifier = Modifier.width(8.dp))

                                            Text(
                                                text = email,
                                                color = Color.White
                                            )

                                        }

                                    }
                                    Spacer(
                                        modifier = Modifier.height(16.dp)
                                    )


                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {


                                        IconButton(
                                            onClick = {

                                                val intent =
                                                    Intent(
                                                        Intent.ACTION_DIAL,
                                                        Uri.parse(
                                                            "tel:${contact.phoneNumber}"
                                                        )
                                                    )

                                                navController.context.startActivity(intent)

                                            }
                                        ) {

                                            Icon(
                                                Icons.Default.Call,
                                                contentDescription = "Call",
                                                tint = Color(0xFF22C55E)
                                            )

                                        }



                                        IconButton(
                                            onClick = {

                                                val intent =
                                                    Intent(
                                                        Intent.ACTION_SENDTO
                                                    ).apply {

                                                        data =
                                                            Uri.parse(
                                                                "smsto:${contact.phoneNumber}"
                                                            )

                                                        putExtra(
                                                            "sms_body",
                                                            "Emergency! Please contact me."
                                                        )

                                                    }


                                                navController.context.startActivity(intent)

                                            }
                                        ) {

                                            Icon(
                                                Icons.Default.Message,
                                                contentDescription = "SMS",
                                                tint = Color(0xFF3B82F6)
                                            )

                                        }



                                        IconButton(
                                            onClick = {

                                                navController.navigate(
                                                    "edit_emergency_contact/" +
                                                            "${contact.id}/" +
                                                            "${contact.name ?: ""}/" +
                                                            "${contact.relationshipType ?: ""}/" +
                                                            "${contact.phoneNumber ?: ""}/" +
                                                            "${contact.email ?: ""}"
                                                )

                                            }
                                        ) {

                                            Icon(
                                                Icons.Default.Edit,
                                                contentDescription = "Edit",
                                                tint = Color.White
                                            )

                                        }



                                        IconButton(
                                            onClick = {

                                                viewModel.deleteContact(
                                                    contact.id,
                                                    userId
                                                ) {

                                                }

                                            }
                                        ) {

                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = Color.Red
                                            )

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

        FloatingActionButton(
            onClick = {

                navController.navigate(
                    Routes.ADD_EMERGENCY_CONTACT
                )

            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {

            Icon(
                Icons.Default.Add,
                contentDescription = "Add Contact"
            )
        }

    }

}