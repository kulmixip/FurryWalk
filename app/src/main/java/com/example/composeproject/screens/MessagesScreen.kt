package com.example.composeproject.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeproject.RetrofitInstance
import com.example.composeproject.data.ConfigViewModel
import com.example.composeproject.data.model.NewMessageRequest
import kotlinx.coroutines.launch

// Screen for showing a specific message between users
@SuppressLint("NewApi")
@Composable
fun MessagesScreen(navController: NavHostController, viewModel: ConfigViewModel, conversationId: String?) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Get the conversation using the conversationId
    val conversation = viewModel.conversations.find { it.id.toString() == conversationId }

    // State to hold the list of chat messages
    val messages = remember { mutableStateListOf<String>() }
    val dog = viewModel.selectedDog



    // Load messages for the selected conversation
    LaunchedEffect(conversation) {
        messages.clear()
        conversation?.messages?.map { "${it.owner_name}: ${it.content}" }?.let { messages.addAll(it) }
    }

    // State to hold the current text input
    var inputText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chat message list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            items(messages.size) { index ->
                Text(
                    text = messages[index],
                    modifier = Modifier.padding(8.dp),
                    color = Color.Black
                )
            }
        }

        // Input field and Send button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                singleLine = true
            )

            Button(
                onClick = {
                    if (inputText.text.isNotBlank()) {
                        coroutineScope.launch {

                            // Create a new message for the conversation
                            val request =  NewMessageRequest(
                                userId = viewModel.id.toString(),
                                ownerId = conversation?.owner_id.toString(),
                                dogId = conversation?.dog_id.toString(),
                                message = inputText.text,
                                sentby = viewModel.id.toString()
                            )

                            // Får ikke riktig verdi, mulig pga mismatch database?
                            if (dog != null) {
                                Toast.makeText(context, "${dog.ownerId}", Toast.LENGTH_SHORT).show()
                            }

                            // Ny message trenger
                            // userId (hvem som skriver) viewmodel
                            // ownerId (hvem som eier) dog.ownerid?
                            // dogId (hvilken hund) dog.id?
                            // message (selve meldingen) inputText
                            // sentby == userId viewmodel

                            try {
                                val response = RetrofitInstance.api.sendMessage(request);

                                if (response != null) {
                                    if (response.status == 200) {
                                        if (conversation != null) {
                                            messages.add("${viewModel.firstName}: ${inputText.text}")
                                        }
                                        inputText = TextFieldValue("")
                                    } else {
                                        Toast.makeText(context, "Send Failed: ${response.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else {
                                    Toast.makeText(context, "yolo", Toast.LENGTH_SHORT).show()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Send Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                Log.e("SendError", "Message sending failed", e)
                            }
                        }
                    } else {
                        Toast.makeText(context, "Input is empty", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}