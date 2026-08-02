package com.usermobilityprediction.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.usermobilityprediction.app.viewmodel.GuardianConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuardianConnectScreen(
    navController: NavController,
    viewModel: GuardianConnectViewModel = viewModel()
){

    var safePathId by remember {
        mutableStateOf("")
    }


    val loading by viewModel.loading.collectAsState()

    val searchedUser by viewModel.user.collectAsState()

    val error by viewModel.error.collectAsState()

    val message by viewModel.message.collectAsState()


    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text(
                        text = "Connect User",
                        color = Color.White
                    )
                },

                colors = TopAppBarDefaults
                    .centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF050505)
                    )

            )

        },

        containerColor = Color(0xFF050505)

    ) { padding ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {


            OutlinedTextField(

                value = safePathId,

                onValueChange = {
                    safePathId = it
                },

                label = {
                    Text("Enter User SafePath ID")
                },

                modifier = Modifier.fillMaxWidth()

            )



            Button(

                onClick = {

                    if (safePathId.isNotBlank()) {

                        viewModel.searchUser(safePathId.trim())

                    }

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Icon(
                    Icons.Default.Search,
                    contentDescription = null
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text("Search User")

            }




            searchedUser?.let { user ->


                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(

                        containerColor =
                            Color.White.copy(alpha = 0.08f)

                    )

                ){


                    Column(

                        modifier = Modifier
                            .padding(20.dp)

                    ){

                        Icon(

                            Icons.Default.Person,

                            contentDescription = null,

                            tint = Color.Blue

                        )


                        Spacer(
                            Modifier.height(10.dp)
                        )


                        Text(

                            text = "User Found",

                            color = Color.White

                        )


                        Text(
                            text = "Name: ${user.full_name}",
                            color = Color.Gray
                        )


                        Text(
                            text = "SafePath ID: ${user.safe_path_id}",
                            color = Color.Gray
                        )


                        Spacer(
                            Modifier.height(15.dp)
                        )


                        Button(

                            onClick = {

                                viewModel.sendRequest(user.safe_path_id)

                            },

                            modifier = Modifier.fillMaxWidth()

                        ){

                            Text(
                                "Send Request"
                            )

                        }
                        message?.let {

                            Spacer(
                                Modifier.height(12.dp)
                            )

                            Text(

                                text = it,

                                color = Color.Green

                            )

                        }


                    }


                }


            }


        }


    }


}