
package com.usermobilityprediction.app.ui.screens

import android.content.Intent
import android.net.Uri

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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


@Composable
fun EmergencyContactsScreen(
    navController: NavController,
    userId: Int,
    viewModel: EmergencyContactViewModel = viewModel()
) {

    val contacts by viewModel.contacts.collectAsState()

    val loading by viewModel.loading.collectAsState()

    val error by viewModel.error.collectAsState()


    // ==========================================================
    // LOAD GUARDIAN-ASSIGNED EMERGENCY CONTACTS
    // ==========================================================

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

            // ==================================================
            // HEADER
            // ==================================================

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
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )

                }

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Column {

                    Text(
                        text = "Emergency Contacts",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    Text(
                        text = "Contacts assigned by your guardian",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                }

            }


            // ==================================================
            // CONTENT
            // ==================================================

            when {

                // ----------------------------------------------
                // LOADING
                // ----------------------------------------------

                loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator(
                            color = Color(0xFF3B82F6)
                        )

                    }

                }


                // ----------------------------------------------
                // ERROR
                // ----------------------------------------------

                error != null -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = error ?: "Unable to load emergency contacts",
                            color = Color.Red,
                            modifier = Modifier.padding(20.dp)
                        )

                    }

                }


                // ----------------------------------------------
                // NO CONTACTS
                // ----------------------------------------------

                contacts.isEmpty() -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = "No Emergency Contacts",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Your guardian has not assigned any emergency contacts yet.",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )

                        }

                    }

                }


                // ----------------------------------------------
                // CONTACTS
                // ----------------------------------------------

                else -> {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),

                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 24.dp
                        ),

                        verticalArrangement = Arrangement.spacedBy(
                            14.dp
                        )
                    ) {

                        items(
                            items = contacts,
                            key = { contact -> contact.id }
                        ) { contact ->


                            Card(
                                modifier = Modifier.fillMaxWidth(),

                                shape = RoundedCornerShape(
                                    20.dp
                                ),

                                colors = CardDefaults.cardColors(
                                    containerColor =
                                        Color.White.copy(
                                            alpha = 0.05f
                                        )
                                )
                            ) {

                                Column(
                                    modifier = Modifier.padding(
                                        18.dp
                                    )
                                ) {


                                    // ==========================
                                    // NAME
                                    // ==========================

                                    Row(
                                        verticalAlignment =
                                            Alignment.CenterVertically
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.Person,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color(0xFF3B82F6),

                                            modifier =
                                                Modifier.size(30.dp)
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.width(12.dp)
                                        )

                                        Column {

                                            Text(
                                                text =
                                                    contact.name
                                                        ?: "Unknown Contact",

                                                color =
                                                    Color.White,

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .titleMedium
                                            )

                                            Text(
                                                text =
                                                    contact.relationshipType
                                                        ?: "Relationship not specified",

                                                color =
                                                    Color(0xFF3B82F6),

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .bodySmall
                                            )

                                        }

                                    }


                                    Spacer(
                                        modifier =
                                            Modifier.height(16.dp)
                                    )


                                    // ==========================
                                    // PHONE
                                    // ==========================

                                    Row(
                                        verticalAlignment =
                                            Alignment.CenterVertically
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.Phone,

                                            contentDescription =
                                                null,

                                            tint =
                                                Color.Gray,

                                            modifier =
                                                Modifier.size(20.dp)
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.width(10.dp)
                                        )

                                        Text(
                                            text =
                                                contact.phoneNumber
                                                    ?: "No phone number",

                                            color =
                                                Color.White,

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodyMedium
                                        )

                                    }


                                    // ==========================
                                    // EMAIL
                                    // ==========================

                                    if (!contact.email.isNullOrBlank()) {

                                        Spacer(
                                            modifier =
                                                Modifier.height(10.dp)
                                        )

                                        Row(
                                            verticalAlignment =
                                                Alignment.CenterVertically
                                        ) {

                                            Icon(
                                                imageVector =
                                                    Icons.Default.Email,

                                                contentDescription =
                                                    null,

                                                tint =
                                                    Color.Gray,

                                                modifier =
                                                    Modifier.size(20.dp)
                                            )

                                            Spacer(
                                                modifier =
                                                    Modifier.width(10.dp)
                                            )

                                            Text(
                                                text =
                                                    contact.email,

                                                color =
                                                    Color.White,

                                                style =
                                                    MaterialTheme
                                                        .typography
                                                        .bodyMedium
                                            )

                                        }

                                    }


                                    Spacer(
                                        modifier =
                                            Modifier.height(18.dp)
                                    )


                                    // ==========================
                                    // CALL + MESSAGE
                                    // ==========================

                                    Row(
                                        modifier =
                                            Modifier.fillMaxWidth(),

                                        horizontalArrangement =
                                            Arrangement.spacedBy(12.dp)
                                    ) {


                                        // --------------------------
                                        // CALL
                                        // --------------------------

                                        androidx.compose.material3.Button(
                                            modifier =
                                                Modifier.weight(1f),

                                            enabled =
                                                !contact.phoneNumber
                                                    .isNullOrBlank(),

                                            onClick = {

                                                val phone =
                                                    contact.phoneNumber
                                                        ?: return@Button

                                                val intent =
                                                    Intent(
                                                        Intent.ACTION_DIAL,
                                                        Uri.parse(
                                                            "tel:$phone"
                                                        )
                                                    )

                                                navController
                                                    .context
                                                    .startActivity(
                                                        intent
                                                    )

                                            }
                                        ) {

                                            Icon(
                                                imageVector =
                                                    Icons.Default.Call,

                                                contentDescription =
                                                    "Call"
                                            )

                                            Spacer(
                                                modifier =
                                                    Modifier.width(6.dp)
                                            )

                                            Text(
                                                text = "Call"
                                            )

                                        }


                                        // --------------------------
                                        // MESSAGE
                                        // --------------------------

                                        androidx.compose.material3.Button(
                                            modifier =
                                                Modifier.weight(1f),

                                            enabled =
                                                !contact.phoneNumber
                                                    .isNullOrBlank(),

                                            onClick = {

                                                val phone =
                                                    contact.phoneNumber
                                                        ?: return@Button

                                                val intent =
                                                    Intent(
                                                        Intent.ACTION_SENDTO
                                                    ).apply {

                                                        data =
                                                            Uri.parse(
                                                                "smsto:$phone"
                                                            )

                                                        putExtra(
                                                            "sms_body",
                                                            "Emergency! Please contact me."
                                                        )

                                                    }

                                                navController
                                                    .context
                                                    .startActivity(
                                                        intent
                                                    )

                                            }
                                        ) {

                                            Icon(
                                                imageVector =
                                                    Icons.Default.Message,

                                                contentDescription =
                                                    "Message"
                                            )

                                            Spacer(
                                                modifier =
                                                    Modifier.width(6.dp)
                                            )

                                            Text(
                                                text = "Message"
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

    }

}

