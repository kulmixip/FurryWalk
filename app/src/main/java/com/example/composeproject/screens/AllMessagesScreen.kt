package com.example.composeproject.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.composeproject.RetrofitInstance
import com.example.composeproject.data.ConfigViewModel
import com.example.composeproject.data.model.DeleteRequest
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllMessagesScreen(navController: NavHostController, viewModel: ConfigViewModel) {
    val conversations = viewModel.conversations
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                        viewModel.selectedDogId = conversation.dog_id
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = lastMessage?.dog_image,
                        contentDescription = "Dog Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Sender and last message column
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Sender ID
                        Text(
                            text = lastMessage?.dog_name ?: "Unknown",
                            color = Color.Black
                        )

                        // Last message content
                        Text(
                            text = lastMessage?.content ?: "No messages yet",
                            color = Color.Gray
                        )
                    }

                    // Delete button
                    Icon(
                        imageVector = Icons.Default.Clear, // Use any delete-related icon here
                        contentDescription = "Delete Conversation",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                scope.launch {
                                    val request = DeleteRequest(
                                        id = conversation.id.toInt()
                                    )

                                    try {
                                        val response =
                                            RetrofitInstance.api.deleteConversation(request)
                                        if (response.status == 200) {
                                            viewModel.removeConversationById(conversation.id.toInt())
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    response.message,
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    } catch (e: Exception) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Could not delete message",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        Log.e("SendError", "Could not delete message", e)
                                    }
                                }
                            },
                        tint = Color.Red,
                    )
                }
            }
        }
    }
}