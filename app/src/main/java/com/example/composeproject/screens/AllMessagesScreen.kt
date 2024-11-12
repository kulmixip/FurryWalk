package com.example.composeproject.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.composeproject.data.ConfigViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllMessagesScreen(navController: NavHostController, viewModel: ConfigViewModel) {

    val conversations = viewModel.conversations

    // LazyColumn to display conversations
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(conversations) { conversation ->
            val imagePainter = rememberImagePainter(
                conversation.user2IdImage,
            )

            // Card for each conversation
            // Add onclick for navigation
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

                    // Image of who the conversation is with
                    Image(
                        painter = imagePainter,
                        contentDescription = "User Image",
                        modifier = Modifier
                            .size(50.dp) // Set the size of the image
                            .padding(end = 16.dp),
                        contentScale = ContentScale.Crop // Crop the image to fit the size
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Name of who the conversation is with
                        Text(text = conversation.user2Id)

                        // Last message in the conversation
                        val lastMessage = conversation.messages.lastOrNull()?.content ?: "No messages yet"
                        Text(
                            text = lastMessage,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
