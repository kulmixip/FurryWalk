package com.example.composeproject.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.composeproject.data.ConfigViewModel
import com.example.composeproject.data.model.Dog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: ConfigViewModel
) {
    val searchQuery = remember { mutableStateOf("") }
    val filteredDogs = remember { mutableStateListOf<Dog>() }

    val onSearchClick: () -> Unit = {
        val query = searchQuery.value.trim()
        Log.d("Homescreen", "searchQuery: $query")
        val searchResults = viewModel.searchDogs(query)
        Log.d("Homescreen", "searchResults: ${viewModel.searchDogs(searchQuery.value)}")
        filteredDogs.clear()
        filteredDogs.addAll(searchResults)
    }

    // Main column layout
    Column(
        modifier = Modifier
            .fillMaxSize() // Take up the entire screen space
            .padding(16.dp), // Applies padding to edges of Column
        horizontalAlignment = Alignment.Start, // Align content to the start horizontally
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add consistent spacing between items
    ) {

        Text(text = "Velkommen " + viewModel.firstName + ' ' + viewModel.lastName)

        // Title
        Text(text = "Pet near you", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        // Description
        Text(text = "Lorem ipsum dolor sit amet..", color = Color.Gray)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Optional padding
            verticalAlignment = Alignment.CenterVertically // Align search bar and button vertically
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery.value, // Current text in text field (empty)
                onValueChange = { newText -> searchQuery.value = newText }, // Handle text change
                modifier = Modifier
                    .weight(1f) // Take up remaining space in the row
                    .padding(end = 8.dp), // Add space between the search bar and button
                placeholder = { Text(text = "Search") }, // Placeholder text
                shape = RoundedCornerShape(12.dp), // Round corners
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, // Use built-in Material Search icon
                        contentDescription = "Search Icon"
                    )
                }
            )

            // Search Button
            Button(
                onClick = onSearchClick, // Action when button is clicked
                modifier = Modifier
                    .height(56.dp), // Match height of the text field for alignment
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                shape = RoundedCornerShape(12.dp) // Optional: match the text field's rounded corners
            ) {
                Text(text = "Search")
            }
        }

        if (filteredDogs.isEmpty()) {
            Text(text = "Found 0 results", color = Color.Gray)
        } else {
            Text(text = "Found ${filteredDogs.size} result(s)")

            // List of filtered dogs
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredDogs) { dog ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Set a height for each dog box
                            .padding(8.dp) // Padding around each dog box
                    ) {
                        // Replace with actual dog image and details
                        AsyncImage(
                            model = dog.image, // Assuming dog.image is a URL or Image
                            contentDescription = dog.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Crop to fit
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dog.breed, // Display the breed of the dog
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = dog.name, // Display the dog's name
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp)) // Space between search bar and category section

        // Category
        Text(
            text = "What activity level do you want?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))

        // Horizontal scrollable row for categories
        Row(
            modifier = Modifier
                .fillMaxWidth() // Full width of row
                .horizontalScroll(rememberScrollState()), // Enable horizontal scrolling
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Space out each chip

        ) {
            // Liste med aktivitetsnivå-kategorier
            val activityLevels = listOf("Low", "Middels", "High")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()), // Gjør raden rullbar horisontalt
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Avstand mellom knappene
            ) {
                activityLevels.forEach { level ->
                    Button(
                        onClick = {
                            // Filtrer hunder basert på aktivitetsnivå
                            val filteredByActivity = viewModel.filterDogsByActivity(level)
                            filteredDogs.clear()
                            filteredDogs.addAll(filteredByActivity)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (level) {
                                "Low" -> Color.Green
                                "Middels" -> Color.Yellow
                                "High" -> Color.Red
                                else -> Color.LightGray
                            }
                        ),
                        shape = CircleShape // Runde knapper
                    ) {
                        Text(
                            text = level,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth() // Full width of row
                    .horizontalScroll(rememberScrollState()), // Enable horizontal scrolling
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Space out each item
            ) {
                viewModel.dogs.forEach { dog ->
                    // Each dog item is wrapped in a Box for the background image and text on top
                    Box(
                        modifier = Modifier
                            .size(200.dp) // Set the size of each item
                            .padding(8.dp) // Add some padding
                            .clickable {
                                // Set the selected dog in the ViewModel
                                viewModel.selectedDog = dog
                                // Navigate to the dog profile screen
                                navController.navigate("dogProfile")
                            }
                    ) {
                        // Load the dog's image as the background
                        AsyncImage(
                            model = dog.image, // Assuming `imageUrl` is the field in your `Dog` data model
                            contentDescription = "Dog Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Crop image to fit
                        )

                        // Overlay text on top of the image
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = dog.name, // Display dog breed
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }

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

            // Navigation messages????
            Row(
                modifier = Modifier
                    .clickable {
                        navController.navigate("allMessages")
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
                Text(text = "All messages")
            }
        }
    }
    }