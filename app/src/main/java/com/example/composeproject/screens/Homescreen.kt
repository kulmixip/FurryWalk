package com.example.composeproject.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add consistent spacing between items
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Open filter */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Filter Icon"
                )
            }
        }

        // Title
        Text(text = "Pet near you", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        // Description
        Text(text = "Lorem ipsum dolor sit amet..", color = Color.Gray)

        // Search Bar
        OutlinedTextField(
            value = "",
            onValueChange = { /* TODO: Handle search */ },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = @androidx.compose.runtime.Composable {
                Icon(
                    imageVector = Icons.Default.Search, // Use built-in Material Search icon
                    contentDescription = "Search Icon"
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category
        Text(text = "Take your tour", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            listOf("Closest to me", "Golden Retriever", "Active", "Private", "Cat").forEach { category ->
                Button(
                    onClick = { /* TODO: Filter by category */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (category == "Golden Retriever") Color.Red else Color.LightGray
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        text = category,
                        color = if (category == "Golden Retriever") Color.White else Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation messages????
        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate("messages")
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically  // Align icon and text vertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                modifier = Modifier.size(48.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))  // Space between icon and text
            Text(text = "Messages")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}