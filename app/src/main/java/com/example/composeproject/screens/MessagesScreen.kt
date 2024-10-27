package com.example.composeproject.screens

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeproject.navigation.BottomNavigationBar

// Screen for showing a specific message between users
@Composable
fun MessagesScreen(navController: NavHostController) {
    // State to hold the list of chat messages
    val messages = remember { mutableStateListOf<String>() }

    // State to hold the current text input
    var inputText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Chat message list
        LazyColumn(
            modifier = Modifier
                .weight(1f)  // Takes up the remaining space above the input
                .fillMaxWidth(),
            reverseLayout = true  // Most recent message at the bottom
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
                        messages.add(0, inputText.text)  // Add message to the top of the list
                        inputText = TextFieldValue("")   // Clear input after sending
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}
