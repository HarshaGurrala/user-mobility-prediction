package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


data class MockSafeZone(
    val name: String,
    val radius: Int,
    val status: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafeZonesScreen(
    navController: NavController,
    userId: Int
) {


    val safeZones = listOf(

        MockSafeZone(
            name = "AITAM College",
            radius = 500,
            status = "Active"
        ),

        MockSafeZone(
            name = "Home",
            radius = 300,
            status = "Active"
        )

    )



    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(
                        text = "Safe Zones",
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

                            imageVector = Icons.Default.ArrowBack,

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



        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF050505))
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp)

        ) {



            items(safeZones) { zone ->



                Card(

                    modifier = Modifier
                        .fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(

                        containerColor =
                            Color.White.copy(alpha = 0.05f)

                    )

                ) {


                    Column(

                        modifier = Modifier
                            .padding(18.dp)

                    ) {



                        Row(

                            verticalAlignment = Alignment.CenterVertically

                        ) {


                            Icon(

                                imageVector =
                                    Icons.Default.LocationOn,

                                contentDescription = null,

                                tint =
                                    Color(0xFF3B82F6)

                            )


                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )


                            Text(

                                text = zone.name,

                                color = Color.White,

                                style =
                                    MaterialTheme.typography.titleMedium

                            )


                        }



                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )


                        Text(

                            text =
                                "Radius: ${zone.radius} meters",

                            color = Color.Gray

                        )


                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )


                        Text(

                            text =
                                "Status: ${zone.status}",

                            color =
                                Color(0xFF22C55E)

                        )


                    }


                }


            }


        }


    }


}