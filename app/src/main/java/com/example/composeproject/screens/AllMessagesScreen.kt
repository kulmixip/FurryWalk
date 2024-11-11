package com.example.composeproject.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.composeproject.data.ConfigViewModel

// Screen for showing all the current messages for the user
@Composable
fun AllMessagesScreen(navController: NavHostController, viewModel: ConfigViewModel) {

    // List of all the messages for the user
    var userId = viewModel.id
    var conversation = viewModel.conversation


}
