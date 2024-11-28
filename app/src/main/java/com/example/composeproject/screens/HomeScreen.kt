package com.example.composeproject.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.composeproject.R
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
    val painter = rememberAsyncImagePainter(model = R.drawable.furrywalk_logo)

    val onSearchClick: () -> Unit = {
        val query = searchQuery.value.trim()
        Log.d("Homescreen", "searchQuery: $query")
        val searchResults = viewModel.searchDogs(query)
        filteredDogs.clear()
        filteredDogs.addAll(searchResults)
    }

    // LazyColumn for full screen scrollability
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome, ${viewModel.firstName} ${viewModel.lastName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Logo
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(75.dp))
                Image(
                    painter = painter,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .height(120.dp)
                        .width(300.dp)
                )
            }
        }

        // Divider
        item {
            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray, thickness = 1.dp)
        }

        // Search Section
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { newText -> searchQuery.value = newText },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text(text = "Search") },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                )

                Button(
                    onClick = onSearchClick,
                    modifier = Modifier.height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Search")
                }
            }
        }

        // Filtered Dogs List
        if (filteredDogs.isEmpty()) {
            item {
                Text(text = "Found 0 results", color = Color.Gray)
            }
        } else {
            item {
                Text(text = "Found ${filteredDogs.size} result(s)")
            }
            items(filteredDogs) { dog ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = dog.image,
                        contentDescription = dog.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = dog.breed,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = dog.name,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // Sort Section
        item {
            Text(text = "Sort on activity level", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val sortDogsByActivityLevel: (String) -> Unit = { level ->
                    filteredDogs.clear()
                    if (level == "All") {
                        filteredDogs.addAll(viewModel.dogs)
                    } else {
                        filteredDogs.addAll(viewModel.dogs.filter { it.activity.equals(level, ignoreCase = true) })
                    }
                }

                // Sorting buttons
                Button(onClick = { sortDogsByActivityLevel("Low") }, shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Low Activity")
                }
                Button(onClick = { sortDogsByActivityLevel("Normal") }, shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Normal Activity")
                }
                Button(onClick = { sortDogsByActivityLevel("High") }, shape = RoundedCornerShape(12.dp)) {
                    Text(text = "High Activity")
                }
                Button(onClick = { sortDogsByActivityLevel("All") }, shape = RoundedCornerShape(12.dp)) {
                    Text(text = "All")
                }
            }
        }

        // Dog Categories
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.dogs.forEach { dog ->
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp)
                            .clickable {
                                viewModel.selectedDog = dog
                                navController.navigate("dogProfile")
                            }
                    ) {
                        AsyncImage(
                            model = dog.image,
                            contentDescription = "Dog Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = dog.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
