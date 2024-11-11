package com.example.composeproject.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeproject.data.ConfigViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllMessagesScreen(navController: NavHostController, viewModel: ConfigViewModel) {
    // List of conversations
    val conversations = viewModel._conversations // Assuming this is a list of Conversation objects

    // LazyColumn to display conversations
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(conversations) { conversation ->
            // Card for each conversation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon of a person on the left
                    Icon(
                        imageVector = Icons.Filled.Person,  // Person icon from Material Design
                        contentDescription = "User Icon",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 16.dp),
                        tint = Color.Gray  // You can change the color of the icon
                    )

                    // Column for the message preview
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Display the user name or conversation title
                        Text(text = "Anders")

                        // Display the last message preview
                        Text(
                            text = "This is a message",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
