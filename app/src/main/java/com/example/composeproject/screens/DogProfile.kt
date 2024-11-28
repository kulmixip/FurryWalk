package com.example.composeproject.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.composeproject.data.ConfigViewModel

@SuppressLint("NewApi")
@Composable
fun DogProfile(
    navController: NavHostController,
    viewModel: ConfigViewModel
) {
    val dog = viewModel.selectedDog

    if (dog != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp) // Add padding for scrolling
        ) {
            // Image with breed and name overlay
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.Gray) // Background color in case image fails to load
                ) {
                    AsyncImage(
                        model = dog.image,
                        contentDescription = "Dog Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Dark overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f)) // Dark transparent background
                    )
                    // Overlay text
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = dog.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = dog.breed,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                    // Back button at the top-left corner of the image overlay
                    IconButton(
                        onClick = {
                            navController.popBackStack() // Navigate back to the previous screen
                        },
                        modifier = Modifier
                            .align(Alignment.TopStart) // Aligns the button to the top-left
                            .padding(16.dp) // Padding around the button
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White // Set the color of the icon
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        // Description text
                        Column(
                            modifier = Modifier.align(Alignment.CenterStart) // Align the text to the start
                        ) {
                            Text(
                                text = "Description",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = dog.description,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }

                        // Message icon button
                        IconButton(
                            onClick = { navController.navigate("messages") }, // Navigate to the "newMessage" screen
                            modifier = Modifier
                                .align(Alignment.CenterEnd) // Align the icon to the end
                                .padding(end = 65.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = "Message",
                                tint = Color.Gray,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Key Features",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Key feature rows
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    KeyFeatureBox(label = "Age", value = dog.age.toString(), modifier = Modifier.weight(1f))
                    KeyFeatureBox(label = "Weight", value = dog.weight.toString(), modifier = Modifier.weight(1f))
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    KeyFeatureBox(label = "Sex", value = dog.sex, modifier = Modifier.weight(1f))
                    KeyFeatureBox(label = "Color", value = dog.color, modifier = Modifier.weight(1f))
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    KeyFeatureBox(label = "Activity", value = dog.activity, modifier = Modifier.weight(1f))
                    KeyFeatureBox(label = "Availability", value = dog.available.toString(), modifier = Modifier.weight(1f))
                }
            }
        }
    } else {
        // Handle case where no dog is selected
        Text(
            text = "No dog selected",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun KeyFeatureBox(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(text = value, fontSize = 16.sp)
        }
    }
}
