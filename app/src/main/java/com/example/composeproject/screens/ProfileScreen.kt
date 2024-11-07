package com.example.composeproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.composeproject.R
import com.example.composeproject.RetrofitInstance
import com.example.composeproject.data.ConfigViewModel
import com.example.composeproject.data.model.UpdateProfileRequest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ConfigViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var isEditing by remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf(TextFieldValue(viewModel.firstName)) }
    var lastName by remember { mutableStateOf(TextFieldValue(viewModel.lastName)) }
    var location by remember { mutableStateOf(TextFieldValue(viewModel.location)) }
    var age by remember { mutableIntStateOf(viewModel.age) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Row for Profile picture and name
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile picture
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(viewModel.image.ifEmpty { "https://www.example.com/default-image.jpg" })
                        .build()
                ),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = viewModel.firstName + " " + viewModel.lastName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = viewModel.location,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        color = Color.Gray.copy(alpha = 0.6f)
                    )
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                IconButton(
                    onClick = {
                        firstName = TextFieldValue(viewModel.firstName)
                        lastName = TextFieldValue(viewModel.lastName)
                        isEditing = !isEditing
                    },
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Profile", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column to stack the phone and email details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${viewModel.phone}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Email Row with icon and email
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.email,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider(thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically // Center the content vertically
        ) {
            // Left Column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${viewModel.rating}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Rating"
                )
            }

            Column {
                VerticalDivider(thickness = 1.dp)
            }

            // Vertical Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .background(Color.Gray.copy(alpha = 0.5f))
            )

            // Right Column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${viewModel.age}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Age"
                )
            }
        }

        HorizontalDivider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(15.dp))

        if(isEditing) {
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        firstName = TextFieldValue(viewModel.firstName)
                        lastName = TextFieldValue(viewModel.lastName)
                        location = TextFieldValue(viewModel.location)
                        isEditing = false
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Cancel")
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val request = UpdateProfileRequest(
                                userId = viewModel.id,
                                firstName = firstName.text,
                                lastName = lastName.text,
                                location = location.text
                            )

                            try {
                                val response = RetrofitInstance.api.updateProfile(request)

                                if (response.status == 200) {
                                    viewModel.firstName = firstName.text
                                    viewModel.lastName = lastName.text
                                    viewModel.location = location.text
                                    isEditing = false
                                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Update Failed: ${response.message}", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Log.e("UpdateProfileError", "Update failed", e)
                            }
                        }
                        isEditing = false
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}
