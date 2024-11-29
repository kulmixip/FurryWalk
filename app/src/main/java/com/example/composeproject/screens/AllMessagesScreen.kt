package com.example.composeproject.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.composeproject.data.ConfigViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllMessagesScreen(navController: NavHostController, viewModel: ConfigViewModel) {
    val conversations = viewModel.conversations

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(conversations) { conversation ->
            // Get the last message (if any)
            val lastMessage = conversation.messages.lastOrNull()

            // Card for each conversation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("messages/${conversation.id}")
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Circle icon or image
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Sender Icon",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                            .padding(8.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Sender and last message column
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Sender ID
                        Text(
                            text = lastMessage?.owner_name ?: "Unknown",
                            color = Color.Black
                        )

                        // Last message content
                        Text(
                            text = lastMessage?.content ?: "No messages yet",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}



